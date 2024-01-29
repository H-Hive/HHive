package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.user.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Value("${EMAIL_USERNAME}")
    private String senderEmail;

    // 랜덤 인증 코드 생성
    public int createNumber() {
        return (int)(Math.random() * (90000)) + 100000;
    }

    // 메일 양식 작성
    public MimeMessage createMail(String mail, int number){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);   // 보내는 이메일
            message.setRecipients(MimeMessage.RecipientType.TO, mail); // 보낼 이메일 설정
            message.setSubject("[HHive] 회원가입을 위한 이메일 인증");  // 제목 설정
            String body = "";
            body += "<h1>" + "안녕하세요. HHive 입니다." + "</h1>";
            body += "<h2>" + "요청하신 인증 번호입니다." + "</h2><br>";
            body += "<h2>" + "아래 코드를 입력해주세요." + "</h2>";

            body += "<div align='center' style='border:1px solid black; font-family:verdana;'>";
            body += "<h2>" + "이메일 인증 코드입니다." + "</h2>";
            body += "<h1 style='color:blue'>" + number + "</h1>";
            body += "</div><br>";
            body += "<h3>" + "HHive를 이용해주셔서 감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    // 이메일 전송 및 인증 코드 반환
    @Transactional
    public int sendEmail(String email) {
        // 이메일 인증 코드 생성
        int verificationCode = createNumber();
        String code = String.valueOf(verificationCode);

        // 메일 전송에 필요한 정보 설정
        MimeMessage message = createMail(email, verificationCode);
        // 실제 메일 전송
        javaMailSender.send(message);

        return verificationCode;
    }


}

