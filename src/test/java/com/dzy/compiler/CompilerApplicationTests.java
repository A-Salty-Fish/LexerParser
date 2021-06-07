package com.dzy.compiler;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompilerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(JSON.toJSONString(new Integer[]{1,2,3,4,5}));
    }

}
