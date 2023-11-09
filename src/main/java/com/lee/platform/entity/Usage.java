package com.lee.platform.entity;

/**
 * @ClassName: Usage
 * @Description: Token消耗
 * @Author: LZX
 * @Date: 2023/11/9 20:41
 */
public record Usage(int promptTokens, int completionTokens, int totalTokens) {}
