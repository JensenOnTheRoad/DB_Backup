package pers.jd.backup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import pers.jd.schedule.DatabaseInfo;

/**
 * @author jensen_deng
 */
@Slf4j
@Component
public class Mysqldump {
  private final DatabaseInfo databaseInfo;

  public Mysqldump(DatabaseInfo databaseInfo) {
    this.databaseInfo = databaseInfo;
  }

  public String allDatabaseFullBackup() throws IOException {
    String content =
        String.format(
            "%s/mysqldump -u%s -p%s --all-databases --single-transaction --flush-logs --source-data >%s/all_databases%s.sql",
            databaseInfo.getMysqlLocation(),
            databaseInfo.getUsername(),
            databaseInfo.getPassword(),
            databaseInfo.getSavePath(),
            System.currentTimeMillis());

    Path path = Paths.get(new File(".").getCanonicalPath() + "/scripts");
    Path pathCreate = Files.createDirectories(path);

    File shell = new File(pathCreate.toAbsolutePath() + "/full_backup.sh");
    if (!shell.exists() && shell.createNewFile()) {
      log.info("Creating a full backup script for all databases.");
    }
    try (FileWriter writer = new FileWriter(shell, false)) {
      writer.write(content);
    }

    return shell.getCanonicalPath();
  }

  /**
   * 脚本文件执行
   *
   * @param fileName 脚本文件名称
   */
  public void scriptExecutor(String fileName) throws IOException, InterruptedException {

    String[] commands = {
      "/bin/bash", "-c", String.format("chmod -R 777 %s && %s ", fileName, fileName)
    };
    ProcessBuilder processBuilder = new ProcessBuilder(commands);
    processBuilder.directory(new File(System.getProperty("user.dir")));

    Process process = processBuilder.start();
    if (process.isAlive()) {
      process.waitFor();
    }

    log.info(IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));
    log.info(IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8));
  }

  /**
   * 检查保存路径是否存在，若不存在则新建
   *
   * @param savePath 保存路径
   */
  private void isExisted(String savePath) throws IOException {
    boolean isExisted = false;
    File file = new File(savePath);
    if (!file.getCanonicalFile().exists()) {
      isExisted = file.getAbsoluteFile().mkdir();
    }
    if (isExisted) {
      log.info("The backup save folder has been generated.");
    }
  }
}
