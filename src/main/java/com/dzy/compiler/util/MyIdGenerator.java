package com.dzy.compiler.util;

import java.util.UUID;

/**
 * @author dzy
 * @title: MyIdGenerator
 * @projectName compiler
 * @description: 符号表id的生成策略
 * @date 2021/6/721:25
 */
public class MyIdGenerator {

    // 策略设置枚举
    public static enum IdGeneratorStrategy {
        UUID,
        AutoIncrecement
    }

    private static ThreadLocal<String> preId = new ThreadLocal<String>(){{set("0");}};

    public static ThreadLocal<IdGeneratorStrategy> strategy = new ThreadLocal<>();

    public static String getId() {
        switch (strategy.get()) {
            case UUID:
                return uuId();
            case AutoIncrecement:
                return incId();
            default:
                return "errorId";
        }
    }

    // 使用uuid策略
    private static String uuId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 使用递增id策略
    private static String incId() {
        Long next = Long.parseLong(preId.get()) + 1L;
        preId.set(next.toString());
        return preId.get();
    }

}
