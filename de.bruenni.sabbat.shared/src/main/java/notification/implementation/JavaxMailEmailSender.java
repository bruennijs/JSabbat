package notification.implementation;

import infrastructure.util.IterableUtils;
import notification.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 24.06.17.
 */
public class JavaxMailEmailSender implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(JavaxMailEmailSender.class);

    @Value("${sabbat.shared.email.username}")
    public String userName;

    @Value("${sabbat.shared.email.password}")
    public String password;

    @Value("${sabbat.shared.email.fromaddress}")
    public String fromAddress;

    @Value("${sabbat.shared.email.hostaddress}")
    public String hostAddress;

    public JavaxMailEmailSender() {
    }

    @Override
    public void sendText(List<String> toAddresses, String subject, String text) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", hostAddress);
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });


        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(fromAddress));
        mimeMessage.setRecipients(Message.RecipientType.TO, parseAddresses(toAddresses));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(text);

        Transport.send(mimeMessage);
    }

    private Address[] parseAddresses(List<String> toAddresses) {
        return toAddresses.stream().map(address ->
        {
            try {
                return new InternetAddress(address);
            } catch (AddressException e) {
                log.error(String.format("Could not parse %1s email adderss!", address), e);
                return null;
            }
        }).collect(Collectors.toList()).toArray(new Address[toAddresses.size()]);
    }
}
