package com.yzx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzx.data.SharedData;
import com.yzx.domain.FlinkJar;
import com.yzx.domain.FlinkJob;
import com.yzx.utils.Utils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping
public class GraphJsonController {
    private String entityJson;

    //保存最初的pom.xml
    private static Document originDocument;

    //pom文件地址
    private static final String pomFilePath="Flink\\pom.xml";

    private String flinkUrl;

    // 将 yml 中的自定义配置注入到这里
    @Value("${file.root.path}")
    private String basePath;

    // 将 yml 中的自定义配置注入到这里
    @Value("${file.root.mvnPath}")
    private String mvnPath;

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
        return flinkUrl;
    }

    /**
     * 获取前端来的JobName并修改pom.xml中的名称
     * @param jobName
     * @return
     */
    @PostMapping("/flinkJobName")
    public String getFlinkJobName(@RequestBody String jobName){
        Utils.jobName = jobName.replace("\"","");
        try{
            // 创建SAXReader对象
            SAXReader sr = new SAXReader(); // 需要导入jar包:dom4j
            // 关联xml
            Document document = sr.read(pomFilePath);

            // 获取根元素
            Element root = document.getRootElement();
            Element artifactId = root.element("artifactId");
            artifactId.setText(Utils.jobName);
            // 调用下面的静态方法完成xml的写出
            saveDocument(document, new File(pomFilePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return jobName;
    }

    //获取从集群来的消息
    @PostMapping("/message")
    public String messageFromFlink(@RequestBody String msg){
        JSONObject object = JSONObject.parseObject(msg);
        Set<String> strings = object.keySet();
        for(String key:strings){
            if(!SharedData.map2msg.containsKey(key)){
                SharedData.map2msg.put(key,new StringBuilder());
            }
            SharedData.map2msg.get(key).append(object.getString(key)).append("\n");
        }
        return msg;
    }

    //前端获取消息
    @PostMapping("/getMessage")
    public String message2Web(@RequestBody String jobId){
        jobId = jobId.replace("\"","");
//        int idx = jobId.indexOf("_");
//        jobId = jobId.substring(idx + 1);
//        int idx1 = jobId.indexOf("-");
//        jobId = jobId.substring(0, idx1);
//        System.out.println(jobId);
//        System.out.println(SharedData.map2msg);
        return SharedData.map2msg.get(jobId).toString();
    }

    //获取集群Jars
    @GetMapping("/getJars")
    public List<FlinkJar> getJars(){
        String str = restTemplate.getForObject(flinkUrl+"/jars",String.class);
//        System.out.println(str);
        JSONObject root = JSONObject.parseObject(str);
        JSONArray jars = root.getJSONArray("files");
        List<FlinkJar> flinkJars = new ArrayList<>();
        for(int i = 0;i<jars.size();++i){
            JSONObject jar =(JSONObject) jars.get(i);
            FlinkJar flinkJar = new FlinkJar();
            flinkJar.setId(jar.getString("id"));
            flinkJar.setName(jar.getString("name"));
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            flinkJar.setUploaded(format1.format(Long.parseLong(jar.getString("uploaded"))));

            flinkJars.add(flinkJar);
//            System.out.println(flinkJob);
        }
        return flinkJars;
    }

    //删除集群指定Jar包
    @PostMapping("/deleteJar")
    public String deleteJar(@RequestBody String jar){
        String jarId = jar.replace("\"","");
        restTemplate.delete(flinkUrl+"/jars/"+jarId);
        return jar;
    }

    //执行集群指定Jar包
    @PostMapping("/runJar")
    public String runJar(@RequestBody String jar){
        String jarId = jar.replace("\"","");
        String str = "";
        restTemplate.postForObject(flinkUrl+"/jars/"+jarId+"/run",str,String.class);
        return jar;
    }

    private static int VERSION = 1;

    /**
     * 任务开始执行
     * @param json
     * @return
     */
    @PostMapping("/graphJson")
    public String graphJson(@RequestBody String json){
        Utils.mvnPath = mvnPath;

        if(flinkUrl==null||flinkUrl.equals(""))return "error";
        String fileid="";
        Utils.deleteFile();

        installJar(Utils.getJarsName(json));

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

        //pom恢复为原来的版本
        try {
            saveDocument(originDocument,new File(pomFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 往maven仓库中添加用户jar，并且写入pom.xml
     * @param jars
     */
    public void installJar(List<String> jars){
        for (int i = 0; i < jars.size(); i++) {
            Utils.execInstallLocalJar(jars.get(i),basePath+jars.get(i));
        }
        insertJarPath2Pom(jars);

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
//        System.out.println(str);
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
//            System.out.println(flinkJob);
        }

        return flinkJobs;
    }

    /**
     * 保存初始版本的pom.xml
     */
    public static void keepOriginPom(){
        try{
            // 创建SAXReader对象
            SAXReader sr = new SAXReader(); // 需要导入jar包:dom4j
            // 关联xml
            originDocument = sr.read(pomFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将用户的jar路径写入pom.xml
     */
    public static void insertJarPath2Pom(List<String> jars) {

        try{
            // 创建SAXReader对象
            SAXReader sr = new SAXReader(); // 需要导入jar包:dom4j
            // 关联xml
            Document document = sr.read(pomFilePath);

            // 获取根元素
            Element root = document.getRootElement();
            Element dependencies = root.element("dependencies");

            for (int i = 0; i < jars.size(); i++) {
                List<String> list = Utils.parseJarName(jars.get(i));
                String artifactId = list.get(0);
                String version = list.get(1);

                Element dependency = dependencies.addElement("dependency");
                Element groupId = dependency.addElement("groupId");
                groupId.setText("org.example");
                Element artifactIdElement = dependency.addElement("artifactId");
                artifactIdElement.setText(artifactId);
                Element versionElement = dependency.addElement("version");
                versionElement.setText(version);
            }
            // 调用下面的静态方法完成xml的写出
            saveDocument(document, new File(pomFilePath));
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
