package com.myproject.vinsky.app.util;

/**
 * 文字列操作用オブジェクト.
 */
public class StringUtil {

    /** Constructor */
    private StringUtil(){}

    /**
     * 文字列が空もしくは、null値であるかを判定する.
     * @param str 文字列
     * @return true:引数文字列は空もしくはnull値. / false:空でもnull値でもない.
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || str.length() == 0) {
            return true;
        }
        return false;
    }
}
