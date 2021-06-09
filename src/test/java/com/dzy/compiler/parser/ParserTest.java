package com.dzy.compiler.parser;

import com.dzy.compiler.lexer.Lexer;
import com.dzy.compiler.util.LexerException;
import com.dzy.compiler.util.Nonterminal;
import com.dzy.compiler.util.ParserException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dzy
 * @title: ParserTest
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/919:56
 */
@SpringBootTest
public class ParserTest {

    @Test
    public void testWordsToTerminal() {
        try {
            Lexer lexer = new Lexer();
            List<String> results = lexer.lex(lexer.readFile("E:\\\\data.txt"));
            Parser parser = new Parser();
            List<String> terminals = parser.wordsToTerminal(results);
            terminals.forEach(terminal -> System.out.println(terminal));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {

        }

    }

    @Test
    public void testParser() {
        Lexer lexer = new Lexer();
        List<String> results = null;
        try {
            results = lexer.lex(lexer.readFile("E:\\\\data.txt"));
            Parser parser = new Parser();
            List<String> terminals = parser.wordsToTerminal(results);
            parser.parse(Nonterminal.START);
        } catch (LexerException | ParserException e) {
            System.out.println(e.getMessage());
        }
    }
}
