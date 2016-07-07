package cn.yuan.luo.json.utils;


/**
 * Created by gluo on 9/8/2015.
 * Use for handling String input type
 */
public class StringUtils {


    public static boolean isNotEmpty(String countStr) {
        return countStr != null && !countStr.trim().isEmpty() && !"null".equalsIgnoreCase(countStr);
    }

    public static String[] split(String str, String regularExpression) {
        if (isNotEmpty(str)) {
            return str.split(regularExpression);
        } else {
            return new String[]{""};
        }
    }

    public static Long toLong(Object number) {
        try {
            number = number.toString().replaceFirst("~", "-");
            return Long.parseLong(number.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(number.toString()).longValue();
            } catch (Exception e1) {
                return 1l;
            }
        }
    }

    public static Double toDouble(Object number) {
        try {
            return Double.parseDouble(number.toString());
        } catch (Exception e) {
                return 1.0d;
        }
    }


}
