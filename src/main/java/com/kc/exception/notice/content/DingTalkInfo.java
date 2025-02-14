package com.kc.exception.notice.content;

import com.kc.exception.notice.enums.DingTalkMsgTypeEnum;
import com.kc.exception.notice.properties.DingTalkProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

import static com.kc.exception.notice.enums.DingTalkMsgTypeEnum.MARKDOWN;
import static com.kc.exception.notice.enums.DingTalkMsgTypeEnum.TEXT;

/**
 * 钉钉通知消息请求体
 *
 * @author kongchong
 */
@Data
public class DingTalkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgtype;

    private DingDingText text;

    private DingDingMarkDown markdown;

    private DingDingAt at;

    private static final String DEFAULT_TITLE = "【%s】%n %s";

    public DingTalkInfo(ExceptionInfo exceptionInfo, DingTalkProperties dingTalkProperties) {
        DingTalkMsgTypeEnum msgType = dingTalkProperties.getMsgType();
        String customeTitle = dingTalkProperties.getCustomeTitle();
        if (msgType.equals(TEXT)) {
            this.text = new DingDingText(String.format(DEFAULT_TITLE, customeTitle, exceptionInfo.createText()));
        } else if (msgType.equals(MARKDOWN)) {
            this.markdown = new DingDingMarkDown(exceptionInfo.getProject(),
                    String.format(DEFAULT_TITLE, customeTitle, exceptionInfo.createDingTalkMarkDown()));
        }
        this.msgtype = msgType.getMsgType();
        this.at = new DingDingAt(dingTalkProperties.getAtMobiles(), dingTalkProperties.getIsAtAll());
    }

    public DingTalkInfo(NormalInfo normalInfo, DingTalkProperties dingTalkProperties) {
        DingTalkMsgTypeEnum msgType = dingTalkProperties.getMsgType();
        String customeTitle = dingTalkProperties.getCustomeTitle();
        if (msgType.equals(TEXT)) {
            this.text = new DingDingText(String.format(DEFAULT_TITLE, customeTitle, normalInfo));
        } else if (msgType.equals(MARKDOWN)) {
            this.markdown = new DingDingMarkDown(normalInfo.getProject(),
                    String.format(DEFAULT_TITLE, customeTitle, normalInfo.createDingTalkMarkDown()));
        }
        this.msgtype = msgType.getMsgType();
        this.at = new DingDingAt(dingTalkProperties.getAtMobiles(), dingTalkProperties.getIsAtAll());
    }

    @Data
    static class DingDingText {

        private String content;

        DingDingText(String content) {
            this.content = content;
        }

    }

    @AllArgsConstructor
    @Data
    static class DingDingMarkDown {

        private String title;

        private String text;

    }

    @AllArgsConstructor
    @Data
    static class DingDingAt {

        private String[] atMobiles;

        private boolean isAtAll;

    }


}
