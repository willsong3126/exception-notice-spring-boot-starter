package com.kc.exception.notice.config;

import com.kc.exception.notice.aop.ExceptionListener;
import com.kc.exception.notice.handler.ExceptionNoticeHandler;
import com.kc.exception.notice.process.DingTalkNoticeProcessor;
import com.kc.exception.notice.process.MailNoticeProcessor;
import com.kc.exception.notice.process.INoticeProcessor;
import com.kc.exception.notice.process.WeChatNoticeProcessor;
import com.kc.exception.notice.properties.DingTalkProperties;
import com.kc.exception.notice.properties.MailProperties;
import com.kc.exception.notice.properties.ExceptionNoticeProperties;
import com.kc.exception.notice.properties.WeChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 异常信息通知配置类
 *
 * @author kongchong
 */
@Configuration
@ConditionalOnProperty(prefix = ExceptionNoticeProperties.PREFIX, name = "enable", havingValue = "true")
@EnableConfigurationProperties(value = ExceptionNoticeProperties.class)
@ComponentScan(basePackages = {"com.kc.exception.notice"})
public class ExceptionNoticeAutoConfiguration {


    @Autowired(required = false)
    private List<INoticeProcessor> noticeProcessors;

    @Autowired
    private ExceptionNoticeProperties properties;

    @Bean(initMethod = "start")
    public ExceptionNoticeHandler noticeHandler() {
        Assert.isTrue(noticeProcessors.size() != 0, "Exception notification configuration is incorrect");
        return new ExceptionNoticeHandler(properties, noticeProcessors);
    }

    @Bean
    @ConditionalOnBean(ExceptionNoticeHandler.class)
    public ExceptionListener exceptionListener(ExceptionNoticeHandler noticeHandler) {
        return new ExceptionListener(noticeHandler);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

}