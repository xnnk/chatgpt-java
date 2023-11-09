package com.lee.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MessageReq
 * @Description: 聊天内容
 * @Author: LZX
 * @Date: 2023/11/9 22:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReq {
    private String message;
}
