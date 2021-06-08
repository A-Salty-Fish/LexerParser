package com.dzy.compiler.lexer;

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
}
