package com.xiaoliu.system.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 61分
 * @date: 2022/9/5 15:23
 * @description:
 */
//@Service
public class MailUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * HTML 文本邮件
     * @param to 接收者邮件
     * @param content HTML内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to,  String content,String blog,String replyUser) throws MessagingException {
        boolean b = checkEmail(to);
        if (b) {
            MimeMessage message = mailSender.createMimeMessage();
            String subject = "来自小刘博客的评论回复";
            String reply = "" +
                    "<html>\n" +
                    "  <body>\n" +
                    "    <div style=\"border-radius: 7px;background: #2b5a8c;box-shadow:  20px 20px 60px #254d77,-20px -20px 60px #3168a1;color:#fafafa;width:400px;height:100px;\">\n" +
                    "      <div style=\"\">\n" +
                    "       来自文章："+blog+
                    "      </div>\n" +
                    "      <div>\n" +
                   "用户:"+ replyUser+"          当前回复您:" +
                    "      </div>\n" +
                    "      <div style=\"text-align:center;color:#f5d3a3\">\n" +
                                content+
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </body>\n" +
                    "</html>";
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(reply, true);
            helper.setFrom(from);
            mailSender.send(message);
        }
    }

//    校验邮箱
    private static boolean checkEmail(String email) {
        if (StringUtils.isEmpty(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(email);
        if (m.matches()){
            return true;
        }
        return false;
    }
}
