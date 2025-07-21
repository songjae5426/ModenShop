package com.songjae.modenshop.mail.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

// 하나의 생성자일 경우에는 @Autowired 생략가능, 두개 이상일 경우 @Autowired할 생성자들 위에 꼭 명시 해줘야함 
@RequiredArgsConstructor // Lombok의 어노테이션으로 final인 필드들의 값들만 인자로 받는 생성자 생성
// 어떠한 클래스 내부에 주입이 필요한 객체가 필드로 사용되면 해당 클래스도 주입이 필요한 클래스로 만들어 줘야 내부 필드들에 주입이 가능하다
@Service // @Component는 객체를 Bean으로 등록 => Spring이 자동으로 객체를 생성하고 관리하게한다(객체 주입)
		// 역활이 명확한 경울에는 @Service,@Repository,@Controller	 => 3개모두 @Component이다 => 역활 구분을 위해 이름만 다름
		// 역활 구분없이 Bean에 등록할때는 @Component 사용
public class EmailService {
	// 이 밑의 두 객체는 스프링Bean으로 스프링을 통해서만 객체를 만들수있다 (스프링에서 주입으로만 객체생성 가능,new사용불가)
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine stringTemplateEngine;

	
	public boolean sendHtmlMail(String receiveEmail, String html) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			mimeMessageHelper.setTo(receiveEmail); // 받는 이메일
			mimeMessageHelper.setSubject("ModenShop 이메일 인증코드입니다!"); // 제목
			mimeMessageHelper.setFrom("songjae5426@naver.com"); // 보내는 이메일
			mimeMessageHelper.setText(html, true); // 두번쨰 인자가 true면 html로 렌더링, false면 일반 텍스트

			javaMailSender.send(mimeMessage); // 이메일 발송

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String authCodeHtml(String authCode) {
		// 타임리프 Context에 데이터를 담아 해당 타임리프로 보냄(컨트롤러에 Model도 내부적으로 context로 변환해 타임리프로 전달하는것이다)
		Context context = new Context();
		context.setVariable("authCode", authCode);
		String html = stringTemplateEngine.process("/email/emailAuthCode", context); // process(템플릿 경로, context) => 템플릿에 값을 전달하고 html을 String으로 반환
		return html;
	}
	
	

	// 세션에 저장된 이메일 인증코드와 사용자가 입력한 이메일 인증코드 체크
	public boolean emailAuthCodeCheck(String userInputAuthCode, String sessionAuthCode) {
		if (userInputAuthCode.equals(sessionAuthCode)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
}
