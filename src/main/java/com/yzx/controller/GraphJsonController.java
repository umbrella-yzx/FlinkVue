package com.yzx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzx.domain.FlinkJob;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping
public class GraphJsonController {
    private String entityJson;

    private String flinkUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取前端的集群URL
     * @param flinkUrl
     * @return
     */
    @PostMapping("/flinkUrl")
    public String getFlinkUrl(@RequestBody String flinkUrl){
        this.flinkUrl = flinkUrl.replace("\"","");
//        System.out.println(flinkUrl);
        return flinkUrl;
    }

    private static int VERSION = 1;

    /**
     * 任务开始执行
     * @param json
     * @return
     */
    @PostMapping("/graphJson")
    public String graphJson(@RequestBody String json){
        if(flinkUrl==null||flinkUrl.equals(""))return "error";

        String fileid="";
        Utils.deleteFile();
//        javaChangeVersion();

        List<String> execute = Utils.execute(entityJson, json,flinkUrl);

        JSONObject object = JSONObject.parseObject(execute.get(0));
        if(object.getString("status").equals("success")){
            String filename = object.getString("filename");
            String[] split = filename.split("/");
            fileid=split[split.length-1];
        }
        String tmp = "";
        restTemplate.postForObject(flinkUrl+"/jars/"+fileid+
                "/run?entry-class=com.yzx.process."+execute.get(1),tmp,String.class);
        return json;
    }

    /**
     * 终止某个Job
     * @param jid 要终止的job的id
     * @return
     */
    @PostMapping("/cancelJob")
    public int cancelJob(@RequestBody String jid){
        String str = "";
        restTemplate.patchForObject(flinkUrl+"/jobs/"+jid.replace("\"",""),str,String.class);
        return 0;
    }

    /**
     * 返回集群中的Jobs
     * @return
     */
    @GetMapping("/jobs")
    public List<FlinkJob> getJobs(){
        String str = "";
        str = restTemplate.getForObject(flinkUrl+"/jobs/overview",String.class);
        System.out.println(str);
        JSONObject root = JSONObject.parseObject(str);
        JSONArray jobs = root.getJSONArray("jobs");
        List<FlinkJob> flinkJobs = new ArrayList<>();
        for(int i = 0;i<jobs.size();++i){
            JSONObject job =(JSONObject) jobs.get(i);
            FlinkJob flinkJob = new FlinkJob();
            flinkJob.setJid(job.getString("jid"));
            flinkJob.setJobName(job.getString("name"));
            flinkJob.setStatus(job.getString("state"));

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            flinkJob.setStartTime(format1.format(Long.parseLong(job.getString("start-time"))));
            if(!job.getString("end-time").equals("-1")){
                flinkJob.setEndTime(format1.format(Long.parseLong(job.getString("end-time"))));
            }
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
            flinkJob.setDuration(format2.format(Long.parseLong(job.getString("duration"))));

            flinkJobs.add(flinkJob);
            System.out.println(flinkJob);
        }

        return flinkJobs;
    }

    /**
     * 恢复初始版本的pom.xml
     */
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

    /**
     * 修改pom.xml文件中的版本
     */
    @Deprecated
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

    /**
     * 固定代码---------可以完成java对XML的写,改等操作
     * @param document
     * @param xmlFile
     * @throws IOException
     */
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

    /**
     * 实体类的Json字符串
     * @param json
     * @return
     */
    @PostMapping("/entityJson")
    public String entityJson(@RequestBody String json){
        entityJson = json;
        return json;
    }
}
