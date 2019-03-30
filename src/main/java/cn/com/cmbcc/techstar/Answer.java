package cn.com.cmbcc.techstar;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 总入口
 */
public class Answer {
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            int topic = Integer.parseInt(args[0]);
            String[] array = new String[args.length-1];
            for (int i = 0; i < args.length-1; i++) {
                array[i] = args[i];
            }
            switch (topic){
                case 2:
                    Answer2.main(array);
            }
        }
    }
}
