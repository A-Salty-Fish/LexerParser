package com.dzy.compiler.lexer;

import com.dzy.compiler.lexer.Status.WordAnalyze;
import com.dzy.compiler.parser.Parser;
import com.dzy.compiler.util.LexerException;

import java.io.File;
import java.io.FileReader;
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
    public List<String> preHandle(String input) {
//        input = input.replaceAll(" ","");
        input = input.replaceAll("\n","");
        String[] rows = input.split(";");
        List<String> output = new LinkedList<>();
        for (String row: rows) {
            if (row.length()!=0) {
                output.add(row);
            }
        }
        return output;
    }

    public List<String> lex(char[] input) throws LexerException {
        return new WordAnalyze().analyze(input);
    }

    public char[] readFile(String fileName) {
        File file = new File(fileName);//定义一个file对象，用来初始化FileReader
        try {
            FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
            int length = (int) file.length();
            //这里定义字符数组的时候需要多定义一个,因为词法分析器会遇到超前读取一个字符的时候，如果是最后一个
            //字符被读取，如果在读取下一个字符就会出现越界的异常
            char buf[] = new char[length+1];
            reader.read(buf);
            reader.close();
            buf[length] = ' ';
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
