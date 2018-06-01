package utils;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/29 19:54
 */
public class ToolUtils {
    public static boolean strIsEmpty(String data) {
        if (null == data || "".equals(data)) {
            return true;
        }
        return false;
    }

    public static String toHump(String in) {
        String out = "";
        boolean nextBig = false;
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == '_') {
                nextBig = true;
                continue;
            }

            if (nextBig == true) {
                char temp= (char) (in.charAt(i) - 32);
                out += temp;
                nextBig=false;
            }else {
                out += in.charAt(i);
            }

        }
        return out;
    }
}
