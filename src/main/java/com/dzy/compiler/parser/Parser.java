package com.dzy.compiler.parser;

import com.dzy.compiler.util.Nonterminal;
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

    List<String> terminals = new LinkedList<>();

    public boolean parse(Nonterminal nonterminal) {
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
            case FUNCTION: {
                if (terminals.size() == 0) {

                }
            }
            default:
                return false;
        }
    }

}
