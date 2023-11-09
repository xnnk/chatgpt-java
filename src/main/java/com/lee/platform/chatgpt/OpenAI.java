package com.lee.platform.chatgpt;

import com.alibaba.fastjson.JSON;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lee.platform.chatgpt.service.OpenAIService;
import com.lee.platform.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: OpenAI
 * @Description: 测试OpenAI对话
 * @Author: LZX
 * @Date: 2023/11/7 19:31
 */
@RestController
@RequestMapping("/gpt")
public class OpenAI {

    @Autowired
    private OpenAIService openAIService;

    private static final String BASE_URL = "xxxxxx";

//    private static final String KEY = System.getenv("OPEN_API_KEY");
    private static final String KEY = "sk-xxxx";

    private static final Gson gson = new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();

    @GetMapping("/models")
    public String testApi() {

        // 创建HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "v1/models"))
                .header("Authorization", "Bearer " + KEY)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            // 发送获取模型列表请求
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            ModelList modelList = JSON.parseObject(response.body(), ModelList.class);
            List<Model> modelSortedList = modelList.data().stream()
                    .sorted(Comparator.comparing(Model::created).reversed())
                    .toList();

            // 获取gpt对话机器人相关的模型
            List<String> gptList = modelSortedList.stream()
                    .map(Model::id)
                    .filter(id -> id.contains("gpt"))
                    .toList();

            // 输出结果
            gptList.forEach(System.out::println);
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "success";
    }

    /**
     * 对话测试
     * @return
     */
    @GetMapping("/chatTest")
    public ChatResponse chatBot() {

        ChatResponse result;

        // 可用的gpt
        List<String> gpts = List.of(
                "gpt-3.5-turbo-1106" ,
                "gpt-3.5-turbo-instruct-0914" ,
                "gpt-3.5-turbo-instruct" ,
                "gpt-3.5-turbo-0613" ,
                "gpt-3.5-turbo-16k-0613" ,
                "gpt-3.5-turbo-16k" ,
                "gpt-3.5-turbo-0301" ,
                "gpt-3.5-turbo"
        );

        // 创建HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // 构建对话请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "v1/chat/completions"))
                .header("Authorization", "Bearer " + KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                                 "model": "gpt-3.5-turbo",
                                 "messages": [{"role": "user", "content": "Say this is a test!"}],
                                 "temperature": 0.7
                            }
                        """))
                .build();

        try {
            // 发送对话请求
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            result = JSON.parseObject(response.body(), ChatResponse.class);

            System.out.println("聊天对话提取: " + result.choices().get(0).message().content());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*
            {
              "id": "xxxxxxx",
              "object": "chat.completion",
              "created": xxxxxx,
              "model": "gpt-3.5-turbo-0613",
              "choices": [
                {
                  "index": 0,
                  "message": {
                    "role": "assistant",            gpt的角色
                    "content": "This is a test!"    内容
                  },
                  "finish_reason": "stop"
                }
              ],
              "usage": {
                "prompt_tokens": 13,     输入消费
                "completion_tokens": 5,  输出消费
                "total_tokens": 18       总共消费
              }
            }
            Response code: 200; Time: 3561ms (3 s 561 ms); Content length: 410 bytes (410 B)
         */
        return result;
    }

    /**
     * 单例聊天
     * @return
     */
    @PostMapping("/single/chat")
    public ChatResponse singletonChat(@RequestBody MessageReq messageReq) {

        System.out.println(messageReq.getMessage());

        // 建立聊天请求体
        ChatRequest chatRequest = openAIService.createChatRequest(messageReq.getMessage());
        ChatResponse result;

        System.out.println(gson.toJson(chatRequest));

        // 创建HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // 构建对话请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "v1/chat/completions"))
                .header("Authorization", "Bearer " + KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(chatRequest)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = JSON.parseObject(response.body(), ChatResponse.class);
            System.out.println("聊天对话提取: " + result.choices().get(0).message().content());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
