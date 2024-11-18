package org.hkmadao.core.base.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameSwitchUtils {

    private static Pattern underlinePattern = Pattern.compile("_(\\w)");
    private static Pattern upperPattern = Pattern.compile("[A-Z]+");

    /**
     * snake case to macro case
     *
     * @param snakeCaseName 下划线命名字符串
     * @return macro case name
     */
    public static String snakeCase2MacroCase(String snakeCaseName) {
        return snakeCaseName.toUpperCase();
    }

    /**
     * 下划线转驼峰（蛇式命名转驼峰命名）
     *
     * @param underline 下划线命名字符串
     * @return 驼峰命名字符串
     */
    public static String snakeCase2CamelCase(String underline) {
        underline = underline.toLowerCase();
        Matcher matcher = underlinePattern.matcher(underline);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 小写驼峰转下划线（驼峰命名转蛇式命名）
     *
     * @param camelCase 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String camelCase2SnakeCaseName(String camelCase) {
        Matcher matcher = upperPattern.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + firstChar2Lower(matcher.group()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 小写驼峰转大写驼峰（帕斯卡命名）
     *
     * @param camelCase 驼峰命名字符串
     * @return 大写驼峰命名字符串
     */
    public static String camelCase2PascalCase(String camelCase) {
        return firstChar2Upper(camelCase);
    }

    /**
     * 大写驼峰转下划线(帕斯卡命名转蛇式命名)
     *
     * @param camelCase 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String pascalCase2SnakeCaseName(String camelCase) {
        Matcher matcher = upperPattern.matcher(camelCase.substring(1));
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group());
        }
        matcher.appendTail(sb);
        sb.insert(0, camelCase.charAt(0));
        return sb.toString().toLowerCase();
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstChar2Upper(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String firstChar2Lower(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static boolean hasText(CharSequence str) {
        if (!StringUtils.hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 小写驼峰转get方法名称
     *
     * @param camelCase 驼峰命名字符串
     * @return get方法名称
     */
    public static String camelCase2GetMethodName(String camelCase) {
        return "get" + firstChar2Upper(camelCase);
    }

    /**
     * 小写驼峰转set方法名称
     *
     * @param camelCase 驼峰命名字符串
     * @return set方法名称
     */
    public static String camelCase2SetMethodName(String camelCase) {
        return "set" + firstChar2Upper(camelCase);
    }

    public static void main(String[] args) {
        System.out.println(snakeCase2CamelCase("class_name"));
        System.out.println(camelCase2SnakeCaseName("classNName"));
    }

}
