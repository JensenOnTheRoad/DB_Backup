package pers.jd.schedule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import pers.jd.backup.Mysqldump;

/**
 * @author jensen_deng
 */
@Component
@Slf4j
@EnableAsync
@EnableScheduling
public class Schedule {
  public static final String CRON = "0/5 * * * * ?";

  @Resource
  pers.jd.schedule.DatabaseInfo databaseInfo;

  /** 脚本执行任务 */
  @Scheduled(cron = CRON)
  @Async
  public void taskBackup() throws IOException, InterruptedException {

    Mysqldump mysqldump = new Mysqldump(databaseInfo);

    String scriptName = mysqldump.allDatabaseFullBackup();
    mysqldump.scriptExecutor(scriptName);

    log.info("当前线程：" + Thread.currentThread().getName() + " 当前时间" + LocalDateTime.now());
  }

  /** 线程池 */
  @Bean("taskExecutor")
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(10);
    taskExecutor.setMaxPoolSize(50);
    taskExecutor.setQueueCapacity(200);
    taskExecutor.setKeepAliveSeconds(60);
    taskExecutor.setThreadNamePrefix("自定义-");
    taskExecutor.setAwaitTerminationSeconds(60);
    return taskExecutor;
  }
}
