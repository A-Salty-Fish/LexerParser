package com.dzy.compiler.lexer;

import com.dzy.compiler.lexer.Status.WordAnalyze;
import com.dzy.compiler.parser.Parser;
import com.dzy.compiler.util.LexerException;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dzy
 * @title: Lexer
 * @projectName compiler
 * @description: Lexer类
 * @date 2021/6/721:13
 */
public class Lexer {

    public ThreadLocal<LinkedHashMap<String,String>> result;

    // 预处理
    public char[] preHandle(char[] input) {
//        input = input.replaceAll(" ","");
        List<Character> tmp = new ArrayList<>();
        for (int i=0;i<input.length;i++) {
            // 去除注解
            if (input[i]=='#') {
                while(input[i]!='\n') {
                    i++;
                }
            }
            tmp.add(input[i]);
        }
        // 加入一个额外的空格作为EOF
        tmp.add(' ');
        char[] result = new char[tmp.size()];
        for (int i=0;i<tmp.size();i++) {
            result[i] = tmp.get(i);
        }
        return result;
    }

    public List<String> lex(char[] input) throws LexerException {
        char[] prehandled = preHandle(input);
        return new WordAnalyze().analyze(prehandled);
    }

    public char[] readFile(String fileName) {
        File file = new File(fileName);//定义一个file对象，用来初始化FileReader
        try {
            FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
            int length = (int) file.length();
            //这里定义字符数组的时候需要多定义一个,因为词法分析器会遇到超前读取一个字符的时候，如果是最后一个
            //字符被读取，如果在读取下一个字符就会出现越界的异常
            char buf[] = new char[length];
            reader.read(buf);
            reader.close();
            return buf;
        } catch (Exception e) {
//            for (String row:results) System.out.println(row);
            System.out.println(e.getMessage());
        }
        finally {

        }
        return null;
    }
}
