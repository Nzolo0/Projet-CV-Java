package io.takima.demo.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service("EmailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Send mail
     * @param to
     * @param subject
     * @param htmlBody
     * @throws MessagingException
     */
    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

    /**
     * Generate a Thymeleaf mail template ( mail.html )
     * @param to
     * @param subject
     * @param templateModel
     * @throws MessagingException
     */
    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

}
