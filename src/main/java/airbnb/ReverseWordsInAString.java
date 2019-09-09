package airbnb;

public class ReverseWordsInAString {
    public static void main(String[] args) {

        String s = "the sky is blue    ";
        s = "  hello world!  ";
//        s = "a good   example";
        s="  hello world!  ";

        System.out.println(reverseWords(s));

    }

    public static String reverseWords(String s) {
        if (s == null || s.trim() == "") {
            return "";
        }
        String ret = "";
        String[] arr = s.split(" ");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[arr.length - 1 - i]);
            if (arr[arr.length - 1 - i].trim().equals(""))
                continue;
            ret += arr[arr.length - 1 - i];
        }
        ret = ret.trim();
        return ret;
    }
}
