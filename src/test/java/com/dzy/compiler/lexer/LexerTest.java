package com.dzy.compiler.lexer;

import com.dzy.compiler.lexer.Status.WordAnalyze;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
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
//        List<String> output = new Lexer().preHandle(input);
//        for (String row:output) {
//            System.out.println(row);
//        }
//        System.out.println(output.size());
    }

    @Test
    public void testWordAna() {
        try {
            Lexer lexer = new Lexer();
            List<String> results = lexer.lex(lexer.readFile("E:\\\\data.txt"));
            for (String result:results) {
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {

        }
    }
}
