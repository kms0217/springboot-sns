package com.kms.mygram.utils;

import java.util.regex.Pattern;

public class Utils {

    public static boolean isBlank(String str) {
        return str == null || str.trim().isBlank();
    }

    public static boolean checkMatch(String pattern, String target) {
        if (target == null)
            return false;
        Pattern p = Pattern.compile(pattern);
        return p.matcher(target).matches();
    }
}
