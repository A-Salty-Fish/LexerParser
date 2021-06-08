package com.dzy.compiler.lexer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author dzy
 * @title: LexerTest
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/721:40
 */
@SpringBootTest
public class LexerTest {

    String input = "a,b,c,d;\n" +
            "if a > b \n" +
            "then {return c+d} \n" +
            "else {return c-d}; \n";

    @Test
    public void testPreHandle() {
        List<String> output = new Lexer().preHandle(input);
        for (String row:output) {
            System.out.println(row);
        }
        System.out.println(output.size());
    }
}
