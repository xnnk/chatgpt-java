package com.lee.platform.entity;

import com.alibaba.fastjson.JSON;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @ClassName: ChatResponse
 * @Description: 聊天响应体
 * @Author: LZX
 * @Date: 2023/11/9 20:41
 */
public record ChatResponse(
        String id,
        String object,
        long created,
        String model,
        Usage usage,
        List<Choice> choices
) {
    @Override
    public String toString() {
        return "ChatResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + LocalDateTime.ofInstant(Instant.ofEpochSecond(created), ZoneId.systemDefault()) +
                ", model='" + model + '\'' +
                ", usage=" + JSON.toJSONString(usage) +
                ", choices=" + JSON.toJSONString(choices) +
                '}';
    }
}
