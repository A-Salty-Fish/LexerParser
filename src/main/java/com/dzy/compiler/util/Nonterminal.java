package com.dzy.compiler.util;

/**
 * @author dzy
 * @title: Nonterminal
 * @projectName compiler
 * @description: 非终结符
 * @date 2021/6/919:54
 */
public enum Nonterminal {
    START, // 初始状态
    FUNCTION, // 函数
    FUNCTIONARGS, //函数参数
    BODY, // 大括号语句块
    LOOP, // 循环块
    LOGIC, // 逻辑表达式
    ASSIGNMENT, // 赋值
    OPERATION,// 算数表达式
    BRANCH, // 分支
}

/* 文法 大写非终结 小写终结 \转义
    START -> FUNCTION START | ε
    FUNCTION -> void valName \( FUNCTIONARGS \) \{ BODY \} | num valName \( FUNCTIONARGS \) \{ BODY return OPERATION \}
    FUNCTIONARGS -> valName \, FUNCTIONARGS | valName | ε
    BODY -> { while LOOP | valName ASSIGNMENT | if BRANCH } BODY | ε
    LOOP -> \( LOGIC \) \{ BODY \} |
    LOGIC -> valName {operator - =} LOGIC | valName | ε
    ASSIGNMENT ->  \= OPERATION ;
    OPERATION -> { constNum | valName } operator OPERATION | constNum | valName
    BRANCH -> \( LOGIC \) \{ BODY \} else \{ BODY \} | if \( LOGIC \) \{ BODY \}
 */