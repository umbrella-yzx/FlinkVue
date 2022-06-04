package com.yzx.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzx.template.entity.Entity;
import com.yzx.template.entity.Property;
import com.yzx.template.entity.PropertyType;
import com.yzx.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class JsonTest {
    public static void main(String[] args) {
        String json ="[\n" +
                "    {\n" +
                "        \"name\":\"testui\",\n" +
                "        \"ziduan\":[\n" +
                "            {\n" +
                "                \"name\":\"t2\",\n" +
                "                \"type\":\"Float\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"classtest\",\n" +
                "        \"ziduan\":[\n" +
                "            {\n" +
                "                \"name\":\"s1\",\n" +
                "                \"type\":\"Float\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"haha\",\n" +
                "        \"ziduan\":[\n" +
                "            {\n" +
                "                \"name\":\"wdnmd\",\n" +
                "                \"type\":\"Short\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"ok\",\n" +
                "                \"type\":\"Long\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"lalal\",\n" +
                "                \"type\":\"Byte\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"KKKK\",\n" +
                "        \"ziduan\":[\n" +
                "            {\n" +
                "                \"name\":\"k1\",\n" +
                "                \"type\":\"Int\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"k3\",\n" +
                "                \"type\":\"Int\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"k2\",\n" +
                "                \"type\":\"Byte\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"k8\",\n" +
                "                \"type\":\"Float\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"haohao\",\n" +
                "        \"ziduan\":[\n" +
                "            {\n" +
                "                \"name\":\"adfja\",\n" +
                "                \"type\":\"Boolean\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"ef\",\n" +
                "                \"type\":\"Double\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";

        //当前 Java 应用程序相关的运行时对象
        Runtime run=Runtime.getRuntime();
        //程序结束时删除所有生成的文件
        run.addShutdownHook(new Thread(Utils::deleteFile));
        Utils.generateEntity(json);
    }
}
