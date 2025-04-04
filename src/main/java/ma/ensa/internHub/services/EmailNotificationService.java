package ma.ensa.internHub.services;

import java.util.Map;

public interface EmailNotificationService {

        void sendPlainTextEmail(String to, String subject, String body);

        void sendEmailWithAttachments(String to, String subject, String body, Map<String, String> attachments);

        void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel,
                        Map<String, String> inlineResources);
}