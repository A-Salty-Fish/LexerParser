package com.dzy.compiler.lexer.Status;

import java.util.LinkedHashMap;

/**
 * @author dzy
 * @title: KeyWord
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/89:27
 */
public class KeyWord {
    public static LinkedHashMap<Integer, String> keyWordsMap;

    public static void addKeyWord(String keyWord) {
        keyWordsMap.put(keyWordsMap.size()+1,keyWord);
    }
    static {
        addKeyWord("常数");
        addKeyWord("变量");
        addKeyWord("var");
        addKeyWord("begin");
        addKeyWord("end");
        addKeyWord("if");
        addKeyWord("else");
        addKeyWord("while");
        addKeyWord("+");
        addKeyWord("-");
        addKeyWord("*");
        addKeyWord("/");
        addKeyWord("=");
    }
}
