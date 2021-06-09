package com.dzy.compiler.parser;

import com.dzy.compiler.util.WordKind;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dzy
 * @title: Parser
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/919:18
 */
public class Parser {
    // 把lexer输出转换成输入格式
    public List<String> wordsToTerminal(List<String> lexerOutput) {
        List<String> result = new LinkedList<>();
        for (String word: lexerOutput) {
            String[] words = word.split("\t");
            result.add(words[1]+"\t"+words[2]);
        }
        return result;
    }

//    public boolean parse() {
//
//    }

}
