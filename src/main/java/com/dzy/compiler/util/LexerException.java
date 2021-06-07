package com.dzy.compiler.util;

/**
 * @author dzy
 * @title: LexerException
 * @projectName compiler
 * @description: Lexer异常基类
 * @date 2021/6/721:12
 */
public class LexerException extends Exception{
    public LexerException() {
        super();
    }
    public LexerException(String s) {
        super(s);
    }
}
