package spring.boot.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  @Autowired
  public JavaMailSender emailSender;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public void sendSimpleEmail(String toEmail, String subject, String context, String... cc) {
    logger.info("Start send email");
    // Create a Simple MailMessage.
    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(context);
    if (cc != null) {
      message.setCc(cc);
    }
    // Send Message!
    this.emailSender.send(message);
    logger.info("Send email successful");
  }
}
