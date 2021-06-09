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
        while(terminals.size()!=0&&getWordName(terminals.get(0)).equals("\\n")) {
            terminals.remove(0); // 移除换行
            curLine++;
        }
    }

    // 更新读头
    private void popTerminal() {
        if (terminals.size()!=0) {
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

    private void throwExceptionWithLine(String message) throws ParserException {
        throw new ParserException(message, curLine.toString());
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
                    throwExceptionWithLine("invalid function signatures");
                }
                String returnType = getWordName(terminals.get(0));
                // 返回值错误
                if (!"void".equals(returnType)&& !"num".equals(returnType)) {
                    throwExceptionWithLine("invalid function return type");
                }
                else {
                    popTerminal(); // 移除返回值
                    WordKind funcNameType = getWordKind(terminals.get(0));
                    // 函数名错误
                    if (funcNameType!=WordKind.valName) {
                        throwExceptionWithLine("invalid function name");
                    }
                    else {
                        popTerminal(); // 移除函数名
                        // 缺少左括号
                        if (terminals.size()==0||!"(".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing (");
                        }
                        popTerminal(); // 移除左括号
                        parse(Nonterminal.FUNCTIONARGS);
                        updateLine();
                        // 缺少右括号
                        if (terminals.size()==0||!")".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing )");
                        }
                        popTerminal(); // 移除右括号
                        // 缺少左大括号
                        if (terminals.size()==0||!"{".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing {");
                        }
                        popTerminal(); // 移除左大括号
                        parse(Nonterminal.BODY);
                        updateLine();
                        //缺少右大括号
                        if (terminals.size()==0||!"}".equals(getWordName(terminals.get(0)))) {
                            throwExceptionWithLine("missing }");
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
                System.out.println(terminals.get(0));
                // ε
                if (")".equals(getWordName(terminals.get(0)))) {
                    return true;
                }
                else {
                    if (!getWordKind(terminals.get(0)).equals(WordKind.valName)) {
                        throwExceptionWithLine("invalid function args");
                    }
                    popTerminal();// 移除一个参数
                    if(",".equals(getWordName(terminals.get(0)))) {
                        popTerminal();
                    }
                    return parse(Nonterminal.FUNCTIONARGS);
                }
            }
            case BODY: {
                
                break;
            }
            default:
                return false;
        }
        return true;
    }

}
