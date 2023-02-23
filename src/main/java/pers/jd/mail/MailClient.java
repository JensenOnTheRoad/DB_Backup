package pers.jd.mail;

import java.io.File;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author jensen
 */
@Component
@Slf4j
public class MailClient {

  @Resource private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String from;

  public void sendMail(
      String sendTo, String subject, String content, Map<String, String> attachmentsMap) {

    try {
      boolean includedAttachment = attachmentsMap.size() > 0;
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, includedAttachment);

      if (includedAttachment) {
        attachmentsMap.forEach(
            (name, path) -> {
              // 附件发送
              try {
                helper.addAttachment(name, new FileSystemResource(new File(path)));
              } catch (MessagingException e) {
                log.error("发送邮件失败:" + e.getMessage());
              }
            });
      }

      helper.setFrom(from);
      helper.setTo(sendTo);
      helper.setSubject(subject);
      helper.setText(content, true);
      mailSender.send(helper.getMimeMessage());

    } catch (MessagingException e) {
      log.error("Email Sending failure.:" + e.getMessage());
    }
  }
}
