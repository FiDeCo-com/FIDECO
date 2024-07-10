package project.boot.fideco.provider;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {

		private final JavaMailSender javaMailSender;
		
		private final String SUBJECT = "[웹사이트] 인증 메일입니다.";
		
		public boolean sendCertificationMail(String email,String CertificationNumber) {
			
			
			try {
			
				MimeMessage message = javaMailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
				
				String htmlContent = getCertificationMessge(CertificationNumber);
				
				messageHelper.setTo(email);
				messageHelper.setSubject(SUBJECT);
				messageHelper.setText(htmlContent, true);
				
				javaMailSender.send(message);		
				
				
			} catch (Exception exception) {
				exception.printStackTrace();
				return false;
			}
			return true;
		}
		
		private String getCertificationMessge(String certificationNumber) {
			
			String certificationMessage = "";
			certificationMessage += "<h1 style='text-align: center;'> [웹사이트] 인증메일</h1>";
			certificationMessage += "<h3 style='text-align: center;'> 인증코드 : <strong style = 'font-sizei : 32px; letter-spacing: 8px;'>"+certificationNumber+"</strong></h3>";
			return certificationMessage;
			
		}
}
