package airbnb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TempTest {

    static { a = 1; }
    static int a = 0;  //  1)如果不赋值呢？ 2)如果这一句与上一句位置互换？
    public static void main(String[] args){
        System.out.println(TempTest.a);
    }


}
