package com.kc.exception.notice.content;

import lombok.Data;

import java.io.Serializable;

/**
 * @author willsong
 * @date 2025/2/13
 */
@Data
public class NormalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目
     */
    private String project;

    /**
     * 内容
     */
    private String content;

    public String createDingTalkMarkDown() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("### 项目名称：").append("\n").append("> ").append(project).append("\n");
        stringBuilder.append("#### 内容：").append("\n").append("> ").append(content).append("\n");
        return stringBuilder.toString();
    }
}
