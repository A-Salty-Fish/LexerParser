package com.dzy.compiler.util;

/**
 * @author dzy
 * @title: Nonterminal
 * @projectName compiler
 * @description: 非终结符
 * @date 2021/6/919:54
 */
public enum Nonterminal {
    program, // 程序
    loop, // 循环
    logic, // 条件
    Operation, // 算数
    assignment, // 赋值
    branch, // 分支
}