package ma.ensa.internHub.services.impl;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.internHub.exception.EmailSendingException;
import ma.ensa.internHub.services.EmailNotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendPlainTextEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("Failed to send plain text email to {}: {}", to, e.getMessage());
            throw new EmailSendingException("Failed to send email to " + to);
        }
    }

    @Override
    public void sendEmailWithAttachments(String to, String subject, String body, Map<String, String> attachments) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(sender);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body);

            if (attachments != null) {
                for (Entry<String, String> attachment : attachments.entrySet()) {
                    String fileName = attachment.getKey();
                    String filePath = attachment.getValue();
                    messageHelper.addAttachment(fileName, new File(filePath));
                }
            }
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Failed to send email with attachments to {}: {}", to, e.getMessage());
            throw new EmailSendingException("Failed to send email to " + to);
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String templateName,
            Map<String, Object> templateModel, Map<String, String> inlineResources) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            messageHelper.setFrom(sender);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            Context context = new Context();
            if (templateModel != null) {
                templateModel.forEach(context::setVariable);
            }
            String htmlContent = templateEngine.process(templateName, context);
            messageHelper.setText(htmlContent, true);

            // Add inline resources (images, etc.)
            if (inlineResources != null) {
                for (Entry<String, String> resource : inlineResources.entrySet()) {
                    messageHelper.addInline(resource.getKey(),
                            new ClassPathResource(resource.getValue()));
                }
            }

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Failed to send HTML email with template to {}: {}", to, e.getMessage());
            throw new EmailSendingException("Failed to send email to " + to);
        }
    }
}