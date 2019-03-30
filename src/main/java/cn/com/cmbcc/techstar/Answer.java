package cn.com.cmbcc.techstar;

/**
 * 总入口
 */
public class Answer {
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            int topic = Integer.parseInt(args[0]);
            String[] array = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                array[i - 1] = args[i];
            }
            switch (topic) {
                case 2:
                    Answer2.main(array);
                    break;
                case 3:
                    Answer3.main(array);
                    break;
                case 6:
                    Answer6.main(array);
            }
        }
    }
}
