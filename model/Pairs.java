package com.xiaobin.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * created by xuweibin at 2024/8/21 13:53
 */
@Getter
@Setter
@AllArgsConstructor
public class Pairs<T, R> {

    private T key;

    private R value;
}
