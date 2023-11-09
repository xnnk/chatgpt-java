package com.lee.platform.chatgpt.service;

import com.lee.platform.entity.ChatRequest;
import com.lee.platform.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OpenAIService
 * @Description:
 * @Author: LZX
 * @Date: 2023/11/9 21:48
 */
@Component
public class OpenAIService {

    /**
     * 创建聊天请求体
     * @param prompt
     * @return
     */
    public ChatRequest createChatRequest(String prompt) {
        System.out.println("create: " + prompt);

        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", List.of(new Message("user", prompt)), 0.7);

        return chatRequest;
    }

}
