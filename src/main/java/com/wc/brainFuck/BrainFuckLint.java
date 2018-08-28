package com.wc.brainFuck;

import java.util.ArrayList;
import java.util.Stack;


class BrainFuckLint {
    /**
     * 1 . TODO 代码压缩 去除注释
     * 2 . 检测括号匹配情况  构造括号位置对应表
     * 3 . 对于括号不能成对情况 给出错误括号位置
     */


    static int[] generateMatchBracket(String code) {

        char[] codes = code.toCharArray();

        //位置的错误信息
        ArrayList<Bracket> bracketError = new ArrayList<>();

        //括号位置匹配表
        int[] matchBracket = new int[codes.length];

        Stack<Bracket> leftBrackets = new Stack<>();

        //括号所在行号  初始是第一行
        int lineNum = 1;

        //括号处在那一行的第几个
        int indexInLine = 0;

        for (int position = 0; position < codes.length; position++) {
            char operator = codes[position];

            if (operator == FuckToken.LEFTBRACKET) {
                //遇到左括号就就将左括号进栈
                leftBrackets.push(new Bracket(position, FuckToken.LEFTBRACKET, lineNum, indexInLine));
            }

            if (operator == FuckToken.RIGHTBRACKET) {
                if (!leftBrackets.isEmpty()) {
                    //栈不为空 就说明栈顶有元素  此时可以构造符号匹配表
                    // 栈顶的左括号与当前的右括号是成对的
                    Bracket bracket = leftBrackets.pop();
                    matchBracket[position] = bracket.position;
                    matchBracket[bracket.position] = position;

                } else {
                    //否则 这个就是无用的右括号  放入错误括号信息中
                    bracketError.add(new Bracket(position, FuckToken.RIGHTBRACKET, lineNum, indexInLine));
                }
            }

            indexInLine++;

            //如果是换行符
            if (operator == FuckToken.LF) {
                lineNum++;
                indexInLine = 0;
            }
        }

        //如果左括号栈还有剩余 就都是错的
        bracketError.addAll(leftBrackets);

        if (!bracketError.isEmpty()) {
            bracketError.forEach(System.err::println);
            System.exit(-1);
        }

        return matchBracket;
    }

}

class Bracket {
    //括号在代码整体中的位置
    int position;

    //括号类型
    private char bracket;

    //括号所在行号
    private int lineNum;

    //括号处在那一行的第几个
    private int indexInLine;

    Bracket(int position, char bracket, int lineNum, int indexInLine) {
        this.position = position;
        this.bracket = bracket;
        this.lineNum = lineNum;
        this.indexInLine = indexInLine;
    }

    @Override
    public String toString() {
        return "-----> unclosed " + bracket + " in line " + lineNum + " index " + indexInLine + "\n";
    }
}
