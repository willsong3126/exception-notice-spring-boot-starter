package com.kc.exception.notice.process;

import com.kc.exception.notice.content.ExceptionInfo;
import com.kc.exception.notice.content.NormalInfo;
import com.kc.exception.notice.properties.ExceptionNoticeProperties;
import com.kc.exception.notice.properties.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 邮箱异常信息通知具体实现
 *
 * @author kongchong
 */
@Component
public class MailNoticeProcessor implements INoticeProcessor {


    @Autowired(required = false)
    private MailSender mailSender;
    @Autowired
    private ExceptionNoticeProperties exceptionNoticeProperties;


    @Override
    public void sendNotice(ExceptionInfo exceptionInfo) {
        MailProperties mailProperties = exceptionNoticeProperties.getMail();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getFrom());
        mailMessage.setTo(mailProperties.getTo());
        if (!StringUtils.isEmpty(mailMessage.getCc())){
            mailMessage.setCc(mailMessage.getCc());
        }
        mailMessage.setText(exceptionInfo.createText());
        mailMessage.setSubject(String.format("来自%s项目的异常通知", exceptionInfo.getProject()));
        mailSender.send(mailMessage);
    }

    /**
     * 正常信息通知
     *
     * @param normalInfo
     */
    @Override
    public void sendNormalNotice(NormalInfo normalInfo) {
        throw new RuntimeException("暂不支持邮件通知");
    }

}
