package com.dzy.compiler.util;

/**
 * @author dzy
 * @title: ParserException
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/921:39
 */
public class ParserException extends Exception{
    ParserException() {
        super();
    }
    ParserException(String message, String line) {
        super(message+" at line "+line);
    }
}
