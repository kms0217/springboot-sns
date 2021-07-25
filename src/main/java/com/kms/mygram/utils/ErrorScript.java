package com.kms.mygram.utils;

public class ErrorScript {

    public static String script(String msg) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<script>");
        stringBuffer.append("alert('" + msg + "');");
        stringBuffer.append("history.back()");
        stringBuffer.append("</script>");
        return stringBuffer.toString();
    }
}
