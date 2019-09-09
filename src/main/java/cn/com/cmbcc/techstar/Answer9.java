package cn.com.cmbcc.techstar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Answer9 {

    private static Map<String, List<String>> Num2Word = new HashMap<>();
    private static Map<String, String> Word2Num = new HashMap<>();
    static String DIC_FILENAME = "simu-chinese.txt";

    public static void main(String[] args) {
        if (args == null || args.length == 1) {
            return;
        }

        String words = "时间倒是肯定就是";
        for (char c : words.toCharArray()) {
            System.out.println(c);
        }


        //分词
        //你好
        //你X
        //X好
        initial(DIC_FILENAME);
        //根据分词


        System.out.println("end");
    }


    private static void splitStr(String word) {
//        word.split()
    }

    public static void initial(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            System.out.println("读文件，fileName=" + fileName);
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] tmpArray = tempString.split(",");
                if (Num2Word.get(tmpArray[0]) == null) {
                    Num2Word.put(tmpArray[0], new ArrayList<>());
                }
                Num2Word.get(tmpArray[0]).add(tmpArray[1]);
                Word2Num.put(tmpArray[1], tmpArray[0]);

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


}