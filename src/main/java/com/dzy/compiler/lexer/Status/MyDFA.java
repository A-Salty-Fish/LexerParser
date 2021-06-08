package com.dzy.compiler.lexer.Status;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dzy
 * @title: MyDFA
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/89:13
 */
public class MyDFA {

    private class HandleSpaceEvent implements MyEvent {
        @Override
        public void handle() {

        }
    }

    public static List<MyTransaction> myTransactionList = new LinkedList<>();

    //初始化转移
    static {

    }




}
