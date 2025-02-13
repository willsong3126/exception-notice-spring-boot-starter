package com.kc.exception.notice.process;

import com.kc.exception.notice.content.DingTalkExceptionInfo;
import com.kc.exception.notice.content.DingTalkResult;
import com.kc.exception.notice.content.ExceptionInfo;
import com.kc.exception.notice.properties.DingTalkProperties;
import com.kc.exception.notice.properties.ExceptionNoticeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * 钉钉异常信息通知具体实现
 *
 * @author kongchong
 */
@Slf4j
@Component
public class DingTalkNoticeProcessor implements INoticeProcessor {

    @Autowired
    @Lazy
    private  RestTemplate restTemplate;
    @Autowired
    private ExceptionNoticeProperties exceptionNoticeProperties;


    @Override
    public void sendNotice(ExceptionInfo exceptionInfo) {
        DingTalkProperties dingTalk = exceptionNoticeProperties.getDingTalk();
        DingTalkExceptionInfo dingDingNotice = new DingTalkExceptionInfo(exceptionInfo,
                dingTalk);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DingTalkExceptionInfo> entity = new HttpEntity<>(dingDingNotice, headers);
        DingTalkResult result = restTemplate.postForObject(dingTalk.getWebHook(), entity, DingTalkResult.class);
        log.debug(String.valueOf(result));
    }

}