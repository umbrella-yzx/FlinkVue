package com.yzx.utils;

public class MyStringUtil {
    public static String ConfigParse(String str){
        if(str==null||str.equals(""))return "";
        String[] configs = str.split(";");
        StringBuilder tmpConfig = new StringBuilder();
        for (String config:configs) {
            if(config!=null&&!config.equals("")){
                String[] split = config.split("=");
                tmpConfig.append(" ").append("'").append(split[0]).append("'='").append(split[1]).append("',");
            }
        }
        return tmpConfig.toString();
    }
}
