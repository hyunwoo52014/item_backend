package kr.happyjob.study.service.login;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import kr.happyjob.study.repository.login.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MailSendService {

    @Autowired
    private LoginMapper loginMapper;

    public void sendEmail(String toEmail, String content, boolean AuthOrPassWord) throws Exception {
        String host = "smtp.gmail.com";
        String subject = "ITAM 인증번호 전달";
        String fromName = "ITAM 관리자";
        String from = "hyunwoo52014@gmail.com"; // 보내는 메일 계정
        String to1 = "xkty vvia gvwi jrex"; // Gmail 앱 비밀번호

        if (AuthOrPassWord) {
            content = "인증번호 [" + content + "]";
        } else {
            content = "비밀번호 [" + content + "]";
        }

        try {
            Properties props = new Properties();
            // G-Mail
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.auth", "true");

            Session mailSession = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 가장 중요, 이메일 계정, 앱 비밀번호 사용해야함
                    // 이후 사용 불능시 따로 구글에서 인증받아서 이메일과 앱 비밀번호 넣을 것
                    return new PasswordAuthentication("hyunwoo52014@gmail.com", "xkty vvia gvwi jrex");
                }
            });
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName, "UTF-8", "B"))); // 보내는 사람
            // 설정
            InternetAddress[] address1 = {new InternetAddress(to1)};
            msg.setRecipients(Message.RecipientType.TO, address1); // 받는 사람 설정
            msg.setSubject(subject); // 제목 설정
            msg.setSentDate(new java.util.Date()); // 보내는 날짜 설정
            msg.setContent(content, "text/html;charset=euc-kr"); // 내용 형식
            // 설정(HTML
            // 형식)
            Transport.send(msg); // 메일 보내기
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //난수 사용 - 6자리
    public String RandomNum() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i <= 6; i++) {
            int n = (int) (Math.random() * 10);
            buffer.append(n);
        }
        return buffer.toString();
    }

    public int searchUserExist(Map<String, Object> paramMap) throws Exception {
        return loginMapper.searchUserExist(paramMap);
    }
}