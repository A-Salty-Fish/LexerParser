package com.dzy.compiler.parser;

import com.dzy.compiler.util.Nonterminal;
import com.dzy.compiler.util.ParserException;
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
        terminals = result;
        return result;
    }

    private List<String> terminals = new LinkedList<>(); // 待处理的终结符
    private Integer curLine = 1; // 当前处理的行号

    // 更新换行
    private void updateLine() {
        while(terminals.size()!=0&& "\\n".equals(getWordName(terminals.get(0)))) {
            terminals.remove(0); // 移除换行
            curLine++;
        }
    }

    // 更新读头
    private void popTerminal() {
        if (terminals.size()!=0) {
//            System.out.println(terminals.get(0));
            terminals.remove(0);
            updateLine();
        }
    }

    private String getWordName(String terminal) {
        return terminal.split("\t")[1];
    }

    private WordKind getWordKind(String terminal) {
        return WordKind.values()[Integer.parseInt(terminal.split("\t")[0])];
    }

    private void throwExceptionWithLine(String message, Nonterminal nonterminal) throws ParserException {
        Integer lastLine = curLine-1;
        throw new ParserException(nonterminal.toString() +":" + message, lastLine.toString() +"-"+ curLine.toString());
    }

    public boolean parse(Nonterminal nonterminal) throws ParserException {
        // 除去空格
        updateLine();
        switch (nonterminal) {
            // START -> FUNCTION START | ε
            case START: {
                if (terminals.size()==0) { // ε
                    return true;
                }
                else {
                    terminals = terminals.subList(0,terminals.size());
                    return parse(Nonterminal.FUNCTION) && parse(Nonterminal.START);
                }
            }
            // FUNCTION -> void valName \( FUNCTIONARGS \) \{ BODY \} | num valName \( FUNCTIONARGS \) \{ BODY return OPERATION \}
            case FUNCTION: {
                // 参数过少
                if (terminals.size()<2) {
                    throwExceptionWithLine("invalid function signatures",Nonterminal.FUNCTION);
                }
                String returnType = getWordName(terminals.get(0));
                // 返回值错误
                if (!"void".equals(returnType)&& !"num".equals(returnType)) {
                    throwExceptionWithLine("invalid function return type",Nonterminal.FUNCTION);
                }
                else {
                    popTerminal(); // 移除返回值
                    WordKind funcNameType = getWordKind(terminals.get(0));
                    // 函数名错误
                    if (funcNameType!=WordKind.valName) {
                        throwExceptionWithLine("invalid function name",Nonterminal.FUNCTION);
                    }
                    else {
                        popTerminal(); // 移除函数名
                        // 缺少左括号
                        if (terminals.size()==0||!"(".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing (",Nonterminal.FUNCTION);
                        }
                        popTerminal(); // 移除左括号
                        parse(Nonterminal.FUNCTIONARGS);
                        updateLine();
                        // 缺少右括号
                        if (terminals.size()==0||!")".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing )",Nonterminal.FUNCTION);
                        }
                        popTerminal(); // 移除右括号
                        // 缺少左大括号
                        if (terminals.size()==0||!"{".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing {",Nonterminal.FUNCTION);
                        }
                        popTerminal(); // 移除左大括号
                        updateLine();
                        parse(Nonterminal.BODY);
                        updateLine();
                        //缺少右大括号
                        if (terminals.size()==0||!"}".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing }",Nonterminal.FUNCTION);
                        }
                        popTerminal(); // 移除右大括号
                    }
                }
                break;
            }
            // FUNCTIONARGS -> valName \, FUNCTIONARGS | valName | ε
            case FUNCTIONARGS: {
                if (terminals.size()==0) {
                    return false;
                }
//                System.out.println(terminals.get(0));
                // ε
                if (")".equals(getWordName(terminals.get(0)))) {
                    return true;
                }
                else {
                    if (!getWordKind(terminals.get(0)).equals(WordKind.valName)) {
                        throwExceptionWithLine("invalid function args",Nonterminal.FUNCTIONARGS);
                    }
                    popTerminal();// 移除一个参数
                    updateLine();
                    if(",".equals(getWordName(terminals.get(0)))) {
                        popTerminal();
                        updateLine();
                    }
                    return parse(Nonterminal.FUNCTIONARGS);
                }
            }
            case BODY: {
                updateLine();
                if (terminals.size()==1) {
                    return false;
                }
                // ε
                if ("}".equals(getWordName(terminals.get(0)))) {
                    return true;
                }
                updateLine();
                // BODY -> { while LOOP | valName ASSIGNMENT | if BRANCH } BODY
                if ("while".equals(getWordName(terminals.get(0)))) {
                    popTerminal(); // 移除while
                    updateLine();
                    parse(Nonterminal.LOOP);
                }
                else if (getWordKind(terminals.get(0)).equals(WordKind.valName)) {
                    popTerminal(); // 移除变量名
                    updateLine();
                    parse(Nonterminal.ASSIGNMENT);
                }
                else if ("if".equals(getWordName(terminals.get(0)))) {
                    popTerminal(); // 移除if
                    updateLine();
                    parse(Nonterminal.BRANCH);
                }
                return parse(Nonterminal.BODY);
            }
            case LOOP: {
                parse(Nonterminal.LOGIC);
                if ("{".equals(getWordName(terminals.get(0)))) {
                    popTerminal();
                    updateLine();
                    parse(Nonterminal.BODY);
                    if ("}".equals(getWordName(terminals.get(0)))) {
                        popTerminal();
                        updateLine();
                        return true;
                    }
                    else {
                        throwExceptionWithLine("missing }",Nonterminal.LOOP);
                    }
                }
                else {
                    throwExceptionWithLine("missing {",Nonterminal.LOOP);
                }
                break;
            }
            case LOGIC: { // LOGIC -> valName {operator - =} {valName | constNum} | valName | ε
                if("(".equals(getWordName(terminals.get(0)))) {
                    popTerminal(); // 移除左括号
                    updateLine();
                    if (WordKind.valName.equals(getWordKind(terminals.get(0)))) {
                        popTerminal(); // 移除变量名
                        if (")".equals(getWordName(terminals.get(0)))) { // valName
                            popTerminal(); // 移除右括号
                            updateLine();
                            return true;
                        }
                        else if (WordKind.operator.equals(getWordKind(terminals.get(0)))) {
                            popTerminal(); // 移除运算符
                            updateLine();
                            if (WordKind.valName.equals(getWordKind(terminals.get(0)))||
                                    WordKind.constNum.equals(getWordKind(terminals.get(0)))) {
                                popTerminal();// 移除常数或变量
                                updateLine();
                            }
                            else {
                                throwExceptionWithLine("missing valname or constNum", Nonterminal.LOGIC);
                            }
                            if (")".equals(getWordName(terminals.get(0)))) {
                                popTerminal();
                                updateLine();
                                return true;
                            }
                            else {
                                throwExceptionWithLine("missing )",Nonterminal.LOGIC);
                            }
                        }
                        else {
                            throwExceptionWithLine("invalid logic", Nonterminal.LOGIC);
                        }
                    }
                    else if (")".equals(getWordName(terminals.get(0)))) {
                        popTerminal();
                        updateLine();
                        return true;
                    }
                    else {
                        throwExceptionWithLine("invalid logic without valname",Nonterminal.LOGIC);
                    }
                }
                else {
                    throwExceptionWithLine("missing (",Nonterminal.LOGIC);
                }
                break;
            }
            case ASSIGNMENT: {
                if ("=".equals(getWordName(terminals.get(0)))) {
                    popTerminal();// 移除等于号
                    updateLine();
                    parse(Nonterminal.OPERATION);
                    updateLine();
                    if (";".equals(getWordName(terminals.get(0)))) {
                        popTerminal();//移除分号
                        return true;
                    }
                    else {
                        throwExceptionWithLine("missing ;", Nonterminal.ASSIGNMENT);
                    }
                }
                else {
                    throwExceptionWithLine("invalid assignment",Nonterminal.ASSIGNMENT);
                }
                break;
            }
            case OPERATION: { // OPERATION -> { constNum | valName } operator OPERATION | constNum | valName
                if (WordKind.valName.equals(getWordKind(terminals.get(0)))||
                WordKind.constNum.equals(getWordKind(terminals.get(0)))) {
                    popTerminal();
                    updateLine();
                    if (";".equals(getWordName(terminals.get(0)))) {
                        return true;
                    }
                    else {
                        if (WordKind.operator.equals(getWordKind(terminals.get(0)))) {
                            popTerminal();
                            updateLine();
                            parse(Nonterminal.OPERATION);
                        }
                    }
                }
                else {
                    throwExceptionWithLine("invalid operatrion", Nonterminal.OPERATION);
                }
                break;
            }
            // BRANCH -> \( LOGIC \) \{ BODY \} else \{ BODY \} | if \( LOGIC \) \{ BODY \}
            case BRANCH: {
                parse(Nonterminal.LOGIC);
                updateLine();
                if ("{".equals(getWordName(terminals.get(0)))) {
                    popTerminal();
                    updateLine();
                    parse(Nonterminal.BODY);
                    if ("}".equals(getWordName(terminals.get(0)))) {
                        popTerminal();
                        updateLine();
                    }
                    else {
                        throwExceptionWithLine("missing }",Nonterminal.BRANCH);
                    }
                    updateLine();
                    if ("else".equals(getWordName(terminals.get(0)))) {
                        popTerminal();
                        updateLine();
                        if ("{".equals(getWordName(terminals.get(0)))) {
                            popTerminal();
                            updateLine();
                            parse(Nonterminal.BODY);
                            if ("}".equals(getWordName(terminals.get(0)))) {
                                popTerminal();
                                updateLine();
                                return true;
                            }
                            else {
                                throwExceptionWithLine("missing }",Nonterminal.BRANCH);
                            }
                        }
                    }
                }
                else {
                    throwExceptionWithLine("missing {",Nonterminal.BRANCH);
                }
                return true;
            }
            default:
                return false;
        }
        return true;
    }

}
