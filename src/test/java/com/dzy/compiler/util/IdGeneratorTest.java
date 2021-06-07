package com.dzy.compiler.util;

import com.dzy.compiler.util.MyIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashSet;

/**
 * @author dzy
 * @title: IdGeneratorTest
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/721:40
 */
@SpringBootTest
public class IdGeneratorTest {

    @Test
    public void testIncId() {
        MyIdGenerator.strategy.set(MyIdGenerator.IdGeneratorStrategy.AutoIncrecement);
        for (int i=0;i<20;i++) {
            System.out.println(MyIdGenerator.getId());
        }
        Assert.isTrue(MyIdGenerator.getId().equals("21"));
    }

    @Test
    public void testuuId() {
        MyIdGenerator.strategy.set(MyIdGenerator.IdGeneratorStrategy.UUID);
        HashSet<String> set = new HashSet<>();
        for (int i=0;i<100;i++) {
            String uuid = MyIdGenerator.getId();
            set.add(uuid);
            System.out.println(uuid);
        }
        Assert.isTrue(set.size()==100);
    }
}
