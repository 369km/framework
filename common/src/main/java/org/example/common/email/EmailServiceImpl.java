package org.example.common.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    private static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private EmailConfigure emailConfigure;

    private Session mailSession() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", emailConfigure.getHost());
        properties.setProperty("mail.smtp.auth", emailConfigure.getAuth());
        properties.setProperty("mail.transport.protocol", emailConfigure.getProtocol());
        return Session.getInstance(properties);
    }

    @Override
    public void send(Email email) {
        try {
            Transport transport = this.mailSession().getTransport();
            transport.connect(emailConfigure.getHost(), emailConfigure.getPort(), emailConfigure.getAccount(), emailConfigure.getPassword());
            MimeMessage message = new MimeMessage(this.mailSession());
            message.setFrom(new InternetAddress(emailConfigure.getAccount()));
            message.setSubject(email.getTitle());
            message.setContent(email.getContent(), "text/html;charset=utf-8");
            transport.sendMessage(message, new Address[]{new InternetAddress(email.getTo())});
            LOGGER.info("mail:{}",email.toString());
        } catch (MessagingException e) {
            LOGGER.error("send mail fail:{}",e.toString());
        }
    }
}
