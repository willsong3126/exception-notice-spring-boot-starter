package com.kc.exception.notice.process;

import com.kc.exception.notice.content.ExceptionInfo;
import com.kc.exception.notice.content.WeChatExceptionInfo;
import com.kc.exception.notice.properties.ExceptionNoticeProperties;
import com.kc.exception.notice.properties.WeChatProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * 企业微信异常信息通知具体实现
 *
 * @author kongchong
 */
@Component
public class WeChatNoticeProcessor implements INoticeProcessor {


    @Autowired
    @Lazy
    private RestTemplate restTemplate;
    @Autowired
    private ExceptionNoticeProperties exceptionNoticeProperties;

    private final Log logger = LogFactory.getLog(getClass());


    @Override
    public void sendNotice(ExceptionInfo exceptionInfo) {
        WeChatProperties weChatProperties = exceptionNoticeProperties.getWeChat();
        WeChatExceptionInfo weChatNoticeProcessor = new WeChatExceptionInfo(exceptionInfo, weChatProperties);
        String result = restTemplate.postForObject(weChatProperties.getWebHook(), weChatNoticeProcessor, String.class);
        logger.debug(result);
    }

}
