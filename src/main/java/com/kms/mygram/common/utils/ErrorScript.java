package com.kms.mygram.common.utils;

public class ErrorScript {

    public static String script(String msg) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<script>");
        stringBuffer.append("alert('");
        stringBuffer.append(msg);
        stringBuffer.append("');");
        stringBuffer.append("history.back()");
        stringBuffer.append("</script>");
        return stringBuffer.toString();
    }
}
