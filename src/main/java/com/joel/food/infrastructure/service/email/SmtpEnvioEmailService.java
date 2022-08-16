package com.joel.food.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.joel.food.core.email.EmailProperties;
import com.joel.food.domain.service.EnvioEmailService;

public class SmtpEnvioEmailService implements EnvioEmailService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;
	@Override
	public void enviar(Mensagem mensagem) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getRementente());
			helper.setTo(mensagem.getDestinarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(mensagem.getCorpo(), true);
			
			
			mailSender.send(mimeMessage);	
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
		
	}

}
