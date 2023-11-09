package com.lee.platform.entity;

import java.util.List;

/**
 * @ClassName: ModelList
 * @Description: 模型列表
 * @Author: LZX
 * @Date: 2023/11/9 18:53
 */
public record ModelList(String object, List<Model> data) {
}
