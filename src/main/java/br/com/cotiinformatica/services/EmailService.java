package br.com.cotiinformatica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired //classe utilizada para fazer o envio de emails
	private JavaMailSender javaMailSender;
	
	//capturando a configuração do arquivo /application.properties
	@Value("${spring.mail.username}")
	private String userName;
	
	//método utilizado para fazer o envio dos emails
	public void send(String to, String subject, String body) throws Exception {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(userName);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body);
		
		javaMailSender.send(message);
	}
}
