package pers.jd.schedule;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jensen_deng
 */
@Component
@Data
public class DatabaseInfo {

  @Value("${backup.url}")
  private String url;

  @Value("${backup.database}")
  private String database;

  @Value("${backup.username}")
  private String username;

  @Value("${backup.password}")
  private String password;

  @Value("${backup.savaPath}")
  private String savePath;

  @Value("${backup.mysql_location}")
  private String mysqlLocation;
}
