package com.ccx.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    /**
     * 获取url的参数
     *
     * @param url  url
     * @param name 参数名称
     * @return
     */
    public static String getParamByUrl(String url, String name) {
        // 将最后位添加，以配上正则
        url += "&";
        String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);
        if (m.find()) {
            return m.group(0).split("=")[1].replace("&", "");
        } else {
            return "";
        }
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(getParamByUrl("/chat/123?name=aaaa&b=dfads", "name"));
        System.out.println(getParamByUrl("http://127.0.0.1:8080/chat/123?name=aaaa&b=dfads", "name"));
        System.out.println(getParamByUrl("name=aaaa&b=dfads", "name"));
    }
}
