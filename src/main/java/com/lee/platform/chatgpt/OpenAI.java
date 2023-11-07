package com.lee.platform.chatgpt;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @ClassName: OpenAI
 * @Description: 测试OpenAI对话
 * @Author: LZX
 * @Date: 2023/11/7 19:31
 */
@RestController
@RequestMapping("/gpt")
public class OpenAI {

    private static final String BASE_URL = "https://api.openai.com/";

//    private static final String KEY = System.getenv("OPEN_API_KEY");
    private static final String KEY = "sk-xxxxx";

    @GetMapping("/models")
    public String testApi() {

        // 配置代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("server.natappfree.cc", 443));

        // 创建OkHttpClient实例并设置代理
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .proxy(proxy)
                .build();

        // 创建HTTP请求
        Request request = new Request.Builder()
                .url(BASE_URL + "v1/models")
                .header("Authorization", "Bearer " + KEY)
                .header("Content-Type", "application/json")
                .build();

        // 执行请求并处理响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.toString());
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                response.close();
                return responseBody; // 返回响应内容
            } else {
                response.close();
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            // 异常处理
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    }

}
