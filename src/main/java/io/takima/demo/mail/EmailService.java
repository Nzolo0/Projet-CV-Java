package io.takima.demo.mail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface EmailService {

    /**
     * EmailService interface
     */
    void sendMessageUsingThymeleafTemplate(String to,
                                           String subject,
                                           Map<String, Object> templateModel)
            throws IOException, MessagingException;


}
