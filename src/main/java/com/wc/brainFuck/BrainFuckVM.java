package com.wc.brainFuck;

import java.util.Scanner;


class BrainFuckVM {

    //车厢  每个车厢存放 一个char
    private char[] train = new char[16];

    //列车员的当前位置
    private int index = 0;

    //值的范围  据文档描述 每个单元存储的是 ASCII 码 范围是 0-127  但是也有人是用 0-255
    private static final char MIN_CELL_VALUE = 0;
    private static final char MAX_CELL_VALUE = 255;

    private Scanner scanner = new Scanner(System.in);

    private void expanse() {
        int curSize = this.train.length;
        char[] newTrain = new char[curSize + 16];
        System.arraycopy(this.train, 0, newTrain, 0, curSize);
        this.train = newTrain;
    }

    private void reset() {
        for (int i = 0; i < this.train.length; i++) {
            this.train[i] = 0;
        }
        this.index = 0;
    }


    String interprete(String code) {
        this.reset();
        StringBuilder sb = new StringBuilder();

        char[] codes = code.toCharArray();

        int[] matchBracket = BrainFuckLint.generateMatchBracket(code);

        for (int i = 0; i < codes.length; i++) {
            char curValue = this.train[this.index];
            char operator = codes[i];
            switch (operator) {
                case FuckToken.MOVERIGHT:
                    if (this.index >= this.train.length - 1) {
                        this.expanse();
                    }
                    this.index++;

                    break;
                case FuckToken.MOVELEFT:
                    if (this.index != 0) {
                        this.index--;
                    }

                    break;
                case FuckToken.PLUS:
                    this.train[this.index] = (curValue == BrainFuckVM.MAX_CELL_VALUE) ? BrainFuckVM.MIN_CELL_VALUE : ++curValue;

                    break;
                case FuckToken.MINUS:
                    this.train[this.index] = (curValue == BrainFuckVM.MIN_CELL_VALUE) ? BrainFuckVM.MAX_CELL_VALUE : --curValue;

                    break;
                case FuckToken.DOT:
                    System.out.print(curValue);
                    sb.append(curValue);

                    break;
                case FuckToken.COMMA:
                    /*
                     * 	输入内容到指针指向的单元（ASCII码）
                     */
                    System.out.println();
                    char ch = this.scanner.next().charAt(0);
                    this.train[this.index] = ch;
                    //这里实现的不好啦

                    break;
                case FuckToken.LEFTBRACKET:
                    /*
                     * 如果指针指向的单元值为零，向后跳转到对应的 ] 指令的次一指令处
                     *
                     * 其实说白了  就是在 数组里查找 下一个 ] 的位置呗！  直接使用 搜索也可以 但是 每次跳转都需要搜索 性能消耗比较大
                     *
                     * 这里涉及一个   [[]] 符号匹配问题  也是这个解释器的难点所在
                     */
                    if (curValue == 0) {
                        i = matchBracket[i];
                    }

                    break;
                case FuckToken.RIGHTBRACKET:
                    /*
                     * 如果指针指向的单元值不为零，向前跳转到对应的 [ 指令的次一指令处
                     */
                    if (curValue != 0) {
                        i = matchBracket[i];
                    }

                    break;
                default:
                    break;
            }
        }

        return sb.toString();

    }

}