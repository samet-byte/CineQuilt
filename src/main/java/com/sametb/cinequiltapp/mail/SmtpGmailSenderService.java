package com.sametb.cinequiltapp.mail;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;


/**
 * @author Samet Bayat.
 * Date: 30.11.2023 1:34 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

// mail işlemleri

@Service
@RequiredArgsConstructor
public class SmtpGmailSenderService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender emailSender;
    
    public void sendEmail(String toEmail, String subject, String body){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailContentBuilder.getSignature());
            message.setTo(toEmail.trim());
            message.setSubject(subject.trim());
            message.setText(body.trim());
            emailSender.send(message);
            SamTextFormat.Companion.doneMessage(String.format("Mail sent to %s", toEmail));

        } catch (Exception e) {
            SamTextFormat.Companion.errorMessage(String.format("Error sending mail to %s", toEmail));
        }
    }


    /**
     * This class/method/field has been deprecated since version 1.0.0. :(
     */
    @Deprecated(since = "1.0.0", forRemoval = true)
    public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachmentPath) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailContentBuilder.getSignature());
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                FileSystemResource file = new FileSystemResource(new File(attachmentPath));
                helper.addAttachment("AttachmentFileName", file);
            }
            emailSender.send(message);
            System.out.println("Message with attachment sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error sending message with attachment: " + e.getMessage());
        }
    }

    public void sendEmailWithImageAndLink(String toEmail, String subject, String body, String imagePath, String webpageLink) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailContentBuilder.getSignature());
            helper.setTo(toEmail);
            helper.setSubject(subject);

            int width = 100;
            int height = 100;

            String htmlContent = "<p>" + body + "</p><br/>" +
                    "<a href='" + webpageLink + "'>" +
                    "<img src='cid:image' alt='Direct to CineQuilt' width='" + width + "' height='" + height + "'></a>";

            helper.setText(htmlContent, true);

            // Add inline image
            if (imagePath != null && !imagePath.isEmpty()) {
                FileSystemResource image = new FileSystemResource(new File(imagePath));
                helper.addInline("image", image);
            }
            emailSender.send(message);
            System.out.println("Email with image and link sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error sending email with image and link: " + e.getMessage());
        }
    }

    public void defaultRegisterMail(String toEmail, String username){
        String subject = mailContentBuilder.subjectBuilder(username);
        String body = mailContentBuilder.bodyBuilder(username);


        sendEmailWithImageAndLink(
                toEmail,
                subject,
                body,
                mailContentBuilder.getMailImage(),
                mailContentBuilder.getDirectLink()
        );
    }

    public void defaultFarewell(String toEmail, String username){
        String subject = mailContentBuilder.farewellSubjectBuilder(username);
        String body = mailContentBuilder.farewellBodyBuilder(username);
        sendEmail(toEmail, subject, body);
    }

    public void defaultPasswordChanged(String toEmail, String newPassword){
        String subject = mailContentBuilder.passwordChangedSubjectBuilder(toEmail);
        String body = mailContentBuilder.passwordChangedBodyBuilder(toEmail, newPassword);
        sendEmail(toEmail, subject, body);
    }
}


