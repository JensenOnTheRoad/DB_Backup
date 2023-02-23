package pers.jd.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

/**
 * @author jensen_deng
 */
@SpringBootTest
@RequiredArgsConstructor
class MailClientTest {
  private final MailClient mailClient;

  public static final String SENT_TO = "test_mail@163.com";
  public static final String CONTENT = "Welcome.";
  public static final String SUBJECT = "发送邮件测试";

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void testTextMail() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:temp/test.jpg");

    HashMap<String, String> attachmentsMap = new HashMap<>();

    attachmentsMap.put(file.getName(), file.getAbsolutePath());

    mailClient.sendMail(SENT_TO, SUBJECT, CONTENT, attachmentsMap);
  }
}
