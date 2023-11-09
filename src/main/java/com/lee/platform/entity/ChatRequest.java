package com.lee.platform.entity;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @ClassName: ChatRequest
 * @Description: 聊天请求体
 * @Author: LZX
 * @Date: 2023/11/9 20:23
 */
public record ChatRequest(String model, List<Message> messages, double temperature) {
    @Override
    public String toString() {
        return "ChatRequest{" +
                "model='" + model + '\'' +
                ", messages=" + JSON.toJSONString(messages) +
                ", temperature=" + temperature +
                '}';
    }
}

