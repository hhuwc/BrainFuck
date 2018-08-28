package com.wc.brainFuck;


import java.io.*;

/**
 * Created by wc on 2018/8/28.
 */
public class Interpreter {
    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        if (args.length == 0) {
            System.err.println("need 1 argument");
            System.exit(-1);
        }

        File file = new File(args[0]);

        if (file.exists() && !file.isDirectory()) {

            //获取文件后缀
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!"bf".equals(suffix)) {
                System.err.println("need a bf file");
                System.exit(-1);
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                stringBuilder.append(line + "\n");
            }

            BrainFuckVM brainFuckVM = new BrainFuckVM();

            brainFuckVM.interprete(stringBuilder.toString());

        } else {
            System.err.println(args[0] + " not found");
            System.exit(-1);
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("The interpreter spent " + (end - start) + "ms");

    }
}
