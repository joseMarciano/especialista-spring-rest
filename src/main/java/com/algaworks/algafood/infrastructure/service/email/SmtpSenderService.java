package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EmailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpSenderService implements EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;


    public SmtpSenderService(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), true);
            helper.setTo(message.getDestinations().toArray(new String[0]));
            helper.setFrom(this.emailProperties.getFrom());

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailSenderException("Não foi possível enviar email", e);
        }


    }
}
