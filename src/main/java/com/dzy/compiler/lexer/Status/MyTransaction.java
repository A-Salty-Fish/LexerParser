package com.dzy.compiler.lexer.Status;

/**
 * @author dzy
 * @title: MyTransaction
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/89:12
 */
public class MyTransaction {
    public Status curStatus;

    public MyAction action;

    public Status nxtStatus;

    private MyTransaction() {

    }

    public static MyTransaction builder() {
        return new MyTransaction();
    }

    public MyTransaction currentStatus(Status status) {
        this.curStatus = status;
        return this;
    }

    public MyTransaction handleAction(MyAction action) {
        this.action = action;
        return this;
    }

    public MyTransaction nextStatus(Status status) {
        this.nxtStatus = status;
        return this;
    }
}
