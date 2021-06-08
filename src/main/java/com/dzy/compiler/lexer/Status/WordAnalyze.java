package com.dzy.compiler.lexer.Status;

/**
 * @author dzy
 * @title: WordAnalyser
 * @projectName compiler
 * @description: TODO
 * @date 2021/6/89:56
 */

import com.dzy.compiler.util.LexerException;

import java.util.HashSet;

/**
 * 此程序是通过将文件的字符读取到字符数组中去，然后遍历数组，将读取的字符进行
 * 分类并输出
 * @author
 *
 */
public class WordAnalyze {
    private enum WordKind{
        None,
        keyWord,
        valName,
        borderSign,
        constNum,
        operator
    }
    private String keyWord[] = {"break","begin","end","if","else","while",};
    private HashSet<String> keyWordSet = new HashSet<String>(){{
        add("break");
        add("end");
        add("begin");
        add("if");
        add("else");
        add("while");
    }};
    private char ch;
    //判断是否是关键字
    boolean isKey(String str)
    {
        return keyWordSet.contains(str);
    }
    //判断是否是字母
    boolean isLetter(char letter)
    {
        return Character.isLetter(letter);
    }
    //判断是否是数字
    boolean isDigit(char digit)
    {
        return Character.isDigit(digit);
    }
    // 打印二元组
    void printResult(StringBuilder result, WordKind wordKind) throws LexerException {
        switch (wordKind) {
            case keyWord:
                System.out.println("关键字"+"\t"+ wordKind.ordinal() +"\t"+result);
                return;
            case valName:
                System.out.println("标识符"+"\t"+ wordKind.ordinal() +"\t"+result);
                return;
            case borderSign:
                System.out.println("分界符"+"\t"+ wordKind.ordinal() +"\t"+result);
                return;
            case constNum:
                System.out.println("常数  "+"\t"+ wordKind.ordinal() +"\t"+result);
                return;
            case operator:
                System.out.println("运算符"+"\t"+ wordKind.ordinal() +"\t"+result);
                return;
            default:
                return;
        }
    }
    //词法分析
    public void analyze(char[] chars) throws LexerException {
//        StringBuilder arr = new StringBuilder();
        for(int i = 0;i< chars.length;i++) {
            ch = chars[i];
            StringBuilder arr = new StringBuilder();
            // 跳过无意义的输入
            if(ch == ' '||ch == '\t'||ch == '\n'||ch == '\r') {
                continue;
            }
            // 处理字母输入
            else if(isLetter(ch)){
                while(isLetter(ch)||isDigit(ch)){
                    arr.append(ch);
                    ch = chars[++i];
                }
                //回退一个字符
                i--;
                if(isKey(arr.toString())){
                    //关键字
                    printResult(arr,WordKind.keyWord);
                }
                else{
                    //标识符
                    printResult(arr,WordKind.valName);
                }
            }
            // 十进制小数\整数 支持.xxxx写法
            else if(isDigit(ch)||(ch == '.'))
            {
                //.xxxx写法
                if (ch == '.') {
                    arr.append(ch);
                    i++;
                    ch = chars[i];
                    while (isDigit(ch)) {
                        arr.append(ch);
                        i++;
                        ch = chars[i];
                    }
                    if ((arr.length()==1)||(isLetter(ch))) {
                        throw new LexerException("invalid decimal input", arr.append(ch).toString());
                    }
                }
                // 正常的十进制小数\整数
                else {
                    while(isDigit(ch)) {
                        arr.append(ch);
                        ch = chars[++i];
                    }
                    if (ch=='.') {
                        arr.append(ch);
                        ch = chars[++i];
                        while(isDigit(ch)) {
                            arr.append(ch);
                            ch = chars[++i];
                        }
                        // .小数后接了个字母非法
                        if (isLetter(ch)) {
                            throw new LexerException("invalid decimal input", arr.append(ch).toString());
                        }
                    }
                    // 整数后接字母非法
                    if (isLetter(ch)) {
                        throw new LexerException("invalid decimal input", arr.append(ch).toString());
                    }
                }
                //属于常数
                printResult(arr, WordKind.constNum);
            }
            else {
                StringBuilder c = new StringBuilder();
                c.append(ch);
                switch(ch){
                        //运算符
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                            printResult(c,WordKind.operator);
                            ;break;
                        //分界符
                        case '(':
                        case ')':
                        case '[':
                        case ']':
                        case ';':
                        case '{':
                        case '}':
                            printResult(c,WordKind.borderSign);
                            break;
                        //运算符 == 和 =
                        case '=':{
                            ch = chars[++i];
                            if(ch == '=') {
                                printResult(new StringBuilder("=="), WordKind.operator);
                            } else {
                                printResult(new StringBuilder("="), WordKind.operator);
                                i--;
                            }
                        }
                        break;
                        // 运算符 >= 和 =
                        case '>':{
                            ch = chars[++i];
                            if(ch == '=') {
                                printResult(new StringBuilder(">="), WordKind.operator);
                            } else {
                                printResult(new StringBuilder(">"), WordKind.operator);
                                i--;
                            }
                        }
                        break;
                        // 运算符 <= 和 =
                        case '<':{
                            ch = chars[++i];
                            if(ch == '=') {
                                printResult(new StringBuilder("<="), WordKind.operator);
                            } else {
                                printResult(new StringBuilder("<"), WordKind.operator);
                                i--;
                            }
                        }break;
                        //无识别
                        default: throw new LexerException("Inlegal character", String.valueOf(ch));
                    }
            }
        }
    }
}