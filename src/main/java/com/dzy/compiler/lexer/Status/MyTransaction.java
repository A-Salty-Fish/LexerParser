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

    public Character character;

    public Status nxtStatus;

    public MyEvent handleEvent;

    private MyTransaction() {

    }

    public static MyTransaction builder() {
        return new MyTransaction();
    }

    public MyTransaction setCurStatus(Status status) {
        this.curStatus = status;
        return this;
    }

    public MyTransaction setAction(Character character) {
        this.character = character;
        return this;
    }

    public MyTransaction setNxtStatus(Status status) {
        this.nxtStatus = status;
        return this;
    }

    public MyTransaction setEvent(MyEvent event) {
        this.handleEvent = event;
        return this;
    }
}
