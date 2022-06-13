package com.yzx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzx.utils.Utils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping
public class GraphJsonController {
    private String entityJson;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/postJob")
    public int postJob(){

        return 0;
    }

    private static int VERSION = 1;

    @PostMapping("/graphJson")
    public String graphJson(@RequestBody String json){
        String fileid="";
        Utils.deleteFile();
//        javaChangeVersion();

        List<String> execute = Utils.execute(entityJson, json);

        JSONObject object = JSONObject.parseObject(execute.get(0));
        if(object.getString("status").equals("success")){
            String filename = object.getString("filename");
            String[] split = filename.split("/");
            fileid=split[split.length-1];
        }
        String tmp = "";
        restTemplate.postForObject("http://192.168.10.102:8081/jars/"+fileid+
                "/run?entry-class=com.yzx.process."+execute.get(1),tmp,String.class);
        return json;
    }

    //恢复初始版本的pom.xml
    public static void recoverPom(){
        String filePath="E:\\Java\\Workspace\\FlinkVue\\Flink\\pom.xml";

        try{
//            Utils.deleteFile();
            // 创建SAXReader对象
            SAXReader sr = new SAXReader(); // 需要导入jar包:dom4j
            // 关联xml
            Document document = sr.read(filePath);

            // 获取根元素
            Element root = document.getRootElement();
            // 获取version标签
            Element version = root.element("version");

            // 在xml的某一个标签里修改一个属性
            version.setText("1.0-SNAPSHOT");

            //如果存在<scope>provided</scope>则删除
            Element dependencies = root.element("dependencies");
            for (Iterator i = dependencies.elementIterator(); i.hasNext();) {
                Element el = (Element) i.next();
                Element engNameElement = el.element("scope");
                if(engNameElement!=null){
                    el.remove(engNameElement);
                }
            }

            // 调用下面的静态方法完成xml的写出
            saveDocument(document, new File(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void javaChangeVersion() {
        String filePath="E:\\Java\\Workspace\\FlinkVue\\Flink\\pom.xml";

        try{
            // 创建SAXReader对象
            SAXReader sr = new SAXReader(); // 需要导入jar包:dom4j
            // 关联xml
            Document document = sr.read(filePath);

            // 获取根元素
            Element root = document.getRootElement();
            // 获取version标签
            Element version = root.element("version");

            // 在xml的某一个标签里修改一个属性
            version.setText(VERSION+".0-SNAPSHOT");
            Utils.version = VERSION;

//            //如果是第二次访问xml，则添加<scope>provided</scope>
//            if(VERSION==2){
//                Element dependencies = root.element("dependencies");
//                for (Iterator i = dependencies.elementIterator(); i.hasNext();) {
//                    Element el = (Element) i.next();
//                    Element engNameElement = el.element("scope");
//                    if(engNameElement==null){
//                        Element scope = el.addElement("scope");
//                        scope.setText("provided");
//                    }
//                }
//            }

            VERSION++;
            // 调用下面的静态方法完成xml的写出
            saveDocument(document, new File(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 下面的为固定代码---------可以完成java对XML的写,改等操作
    public static void saveDocument(Document document, File xmlFile) throws IOException {
        Writer osWrite = new OutputStreamWriter(new FileOutputStream(xmlFile));// 创建输出流
        OutputFormat format = OutputFormat.createPrettyPrint(); // 获取输出的指定格式
        format.setEncoding("UTF-8");// 设置编码 ，确保解析的xml为UTF-8格式
        XMLWriter writer = new XMLWriter(osWrite, format);// XMLWriter
        // 指定输出文件以及格式
        writer.write(document);// 把document写入xmlFile指定的文件(可以为被解析的文件或者新创建的文件)
        writer.flush();
        writer.close();
    }

    @PostMapping("/entityJson")
    public String entityJson(@RequestBody String json){
        entityJson = json;
        return json;
    }
}
