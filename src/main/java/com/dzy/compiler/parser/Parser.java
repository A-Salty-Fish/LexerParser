package com.dzy.compiler.parser;

/**
 * @author dzy
 * @title: Parser
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/919:18
 */
public class Parser {
    private enum Terminal{
        keyWord,
        valName,
        borderSign,
        constNum,
        operator,
    }

    public enum NoneTerminal {
        program, // 程序
        loop, // 循环
        logic, // 条件
        Operation, // 算数
        assignment, // 赋值
        branch, // 分支
    }


}
