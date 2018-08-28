package com.wc.brainFuck;

import org.testng.Assert;
import org.testng.annotations.Test;


public class BrainFuckVM测试 {

    private BrainFuckVM brainFuckVM = new BrainFuckVM();

    @Test(testName = "输出值应当是 7")
    public void addingTwoValues() {
        /**
         * https://en.wikipedia.org/wiki/Brainfuck
         * 英文维基百科的例子
         */
        String code = "++       Cell c0 = 2\n" +
                "> +++++  Cell c1 = 5\n" +
                "\n" +
                "[        Start your loops with your cell pointer on the loop counter (c1 in our case)\n" +
                "< +      Add 1 to c0\n" +
                "> -      Subtract 1 from c1\n" +
                "]        End your loops with the cell pointer on the loop counter\n" +
                "\n" +
                "At this point our program has added 5 to 2 leaving 7 in c0 and 0 in c1\n" +
                "but we cannot output this value to the terminal since it is not ASCII encoded!\n" +
                "\n" +
                "To display the ASCII character \"7\" we must add 48 to the value 7\n" +
                "48 = 6 * 8 so let's use another loop to help us!\n" +
                "\n" +
                "++++ ++++  c1 = 8 and this will be our loop counter again\n" +
                "[\n" +
                "< +++ +++  Add 6 to c0\n" +
                "> -        Subtract 1 from c1\n" +
                "]\n" +
                "< .        Print out c0 which has the value 55 which translates to \"7\"!";

        String actual = brainFuckVM.interprete(code);
        String expected = "7";
        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "ok 多层嵌套循环测试")
    public void nestedLoopTest() {
        String code = "++++++++[->-[->-[->-[-]<]<]<]>++++++++[<++++++++++>-]<[>+>+<<-]>-.>-----.>";

        long start = System.currentTimeMillis();
        String actual = brainFuckVM.interprete(code);
        long end = System.currentTimeMillis();
        System.out.println("嵌套循环解析耗时 ：" + (end - start) + "ms");
        String expected = "OK";
        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "hello world 测试")
    public void printHelloWorld() {
        String code = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
        String actual = brainFuckVM.interprete(code);
        String expected = "Hello World!\n";
        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "圆周率测试 ")
    public void calcPI() {
        String code = ">  +++++ +++++ +++++ (15 digits)\n" +
                "\n" +
                "[<+>>>>>>>>++++++++++<<<<<<<-]>+++++[<+++++++++>-]+>>>>>>+[<<+++[>>[-<]<[>]<-]>>\n" +
                "[>+>]<[<]>]>[[->>>>+<<<<]>>>+++>-]<[<<<<]<<<<<<<<+[->>>>>>>>>>>>[<+[->>>>+<<<<]>\n" +
                ">>>>]<<<<[>>>>>[<<<<+>>>>-]<<<<<-[<<++++++++++>>-]>>>[<<[<+<<+>>>-]<[>+<-]<++<<+\n" +
                ">>>>>>-]<<[-]<<-<[->>+<-[>>>]>[[<+>-]>+>>]<<<<<]>[-]>+<<<-[>>+<<-]<]<<<<+>>>>>>>\n" +
                ">[-]>[<<<+>>>-]<<++++++++++<[->>+<-[>>>]>[[<+>-]>+>>]<<<<<]>[-]>+>[<<+<+>>>-]<<<\n" +
                "<+<+>>[-[-[-[-[-[-[-[-[-<->[-<+<->>]]]]]]]]]]<[+++++[<<<++++++++<++++++++>>>>-]<\n" +
                "<<<+<->>>>[>+<<<+++++++++<->>>-]<<<<<[>>+<<-]+<[->-<]>[>>.<<<<[+.[-]]>>-]>[>>.<<\n" +
                "-]>[-]>[-]>>>[>>[<<<<<<<<+>>>>>>>>-]<<-]]>>[-]<<<[-]<<<<<<<<]++++++++++.";

        String actual = brainFuckVM.interprete(code);
        String expected = "3.14070455282885\n";
        Assert.assertEquals(actual, expected);
    }

    @Test(testName = "平方测试")
    public void testSquare() {
        String code = "++++[>+++++<-]>[<+++++>-]+<+[\n" +
                "    >[>+>+<<-]++>>[<<+>>-]>>>[-]++>[-]+\n" +
                "    >>>+[[-]++++++>>>]<<<[[<++++++++<++>>-]+<.<[>----<-]<]\n" +
                "    <<[>>>>>[>>>[-]+++++++++<[>-<-]+++++++++>[-[<->-]+[<<<]]<[>+<-]>]<<-]<<-\n" +
                "]";

        String actual = brainFuckVM.interprete(code);
        String[] actuals = actual.split("\n");

        int expectedLength = 101;
        Assert.assertEquals(actuals.length, expectedLength);
        Assert.assertEquals(actuals[0], "0");
        Assert.assertEquals(actuals[100], "10000");
    }


}
