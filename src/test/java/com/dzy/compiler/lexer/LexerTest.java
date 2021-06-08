package com.dzy.compiler.lexer;

import com.dzy.compiler.lexer.Status.WordAnalyze;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
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

    @Test
    public void testWordAna() {

        File file = new File("E:\\data.txt");//定义一个file对象，用来初始化FileReader
        try {
            FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
            int length = (int) file.length();
            //这里定义字符数组的时候需要多定义一个,因为词法分析器会遇到超前读取一个字符的时候，如果是最后一个
            //字符被读取，如果在读取下一个字符就会出现越界的异常
            char buf[] = new char[length+1];
            reader.read(buf);
            reader.close();
            buf[length] = ' ';
            List<String> results = new Lexer().lex(buf);
            for (String row:results) System.out.println(row);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
