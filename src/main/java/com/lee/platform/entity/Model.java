package com.lee.platform.entity;

/**
 * @ClassName: Model
 * @Description: 运行的模型
 * @Author: LZX
 * @Date: 2023/11/9 18:50
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @param id
 * @param object
 * @param created
 * @param ownedBy
 */
public record Model(String id, String object, long created, String ownedBy) {
    // 官方发送的json格式
    /*
        {
          "id": "gpt-3.5-turbo-16k-0613",
          "object": "model",
          "created": 1685474247,
          "owned_by": "openai"
        }
     */

    /**
     * 打印目标时间
     * @return
     */
    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + LocalDateTime.ofInstant(Instant.ofEpochSecond(created), ZoneId.systemDefault()) +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
