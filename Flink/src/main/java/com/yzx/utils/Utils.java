package com.yzx.utils;

import com.yzx.template.Process;
import com.yzx.template.entity.Entity;
import com.yzx.template.operate.OperateFilter;
import com.yzx.template.operate.OperateMap;
import com.yzx.template.source.SourceCSV;
import com.yzx.template.source.SourceJdbc;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static File javaFile = null;

    public static ArrayList<String> generateFile = new ArrayList<>();

    /**
     * 解析数据源配置项
     * @param str
     * @return
     */
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

    /**
     * 生成POJO类
     * @param obj 实体类属性
     */
    public static void generateCode(Object obj, int type, String ftl){
        Configuration cfg = new Configuration();
        try {
            // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
            cfg.setDirectoryForTemplateLoading(new File("template"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());

            // 步骤二：获取 模板文件
            Template template = cfg.getTemplate(ftl);

            // 步骤三：创建 数据模型
            Map<String, Object> root = new HashMap<String, Object>();

            // 创建.java类文件
            File outDirFile = new File("Flink/src/main/java");
            if(!outDirFile.exists()){
                outDirFile.mkdir();
            }

            String path = new String();

            switch (type){
                //Entity
                case 0:{
                    javaFile = toJavaFilename(outDirFile, ((Entity)obj).getJavaPackage(), ((Entity)obj).getClassName());
                    path = ((Entity)obj).getJavaPackage()+"."+((Entity)obj).getClassName();
                    root.put("entity", ((Entity)obj));
                    break;
                }
                //JdbcSource
                case 1:{
                    javaFile = toJavaFilename(outDirFile, ((SourceJdbc)obj).javaPackage, ((SourceJdbc)obj).className);
                    path = ((SourceJdbc)obj).getJavaPackage()+"."+((SourceJdbc)obj).getClassName();
                    root.put("jdbc", ((SourceJdbc)obj));
                    break;
                }
                //Filter
                case 2:{
                    javaFile = toJavaFilename(outDirFile, ((OperateFilter)obj).javaPackage, ((OperateFilter)obj).className);
                    path = ((OperateFilter)obj).getJavaPackage()+"."+((OperateFilter)obj).getClassName();
                    root.put("filter", ((OperateFilter)obj));
                    break;
                }
                //Map
                case 3:{
                    javaFile = toJavaFilename(outDirFile, ((OperateMap)obj).javaPackage, ((OperateMap)obj).className);
                    path = ((OperateMap)obj).getJavaPackage()+"."+((OperateMap)obj).getClassName();
                    root.put("map", ((OperateMap)obj));
                    break;
                }
                //Process
                case 4:{
                    javaFile = toJavaFilename(outDirFile, ((Process)obj).javaPackage, ((Process)obj).className);
                    path = ((Process)obj).getJavaPackage()+"."+((Process)obj).getClassName();
                    root.put("process", ((Process)obj));
                    break;
                }
                //CSV
                case 5:{
                    javaFile = toJavaFilename(outDirFile, ((SourceCSV)obj).javaPackage, ((SourceCSV)obj).className);
                    path = ((SourceCSV)obj).getJavaPackage()+"."+((SourceCSV)obj).getClassName();
                    root.put("csv", ((SourceCSV)obj));
                    break;
                }
                default:break;
            }

            // 步骤四：合并 模板 和 数据模型
            // 创建.java类文件
            if(javaFile != null){
                Writer javaWriter = new FileWriter(javaFile);
                template.process(root, javaWriter);
                javaWriter.flush();
                System.out.println("文件生成路径：" + javaFile.getCanonicalPath());
                generateFile.add(javaFile.getCanonicalPath());
                javaWriter.close();
            }
            // 输出到Console控制台
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
//            out.flush();
//            out.close();
            JavaCompiler compiler= ToolProvider.getSystemJavaCompiler(); //调用动态编译的工具
            int result =compiler.run(null, null, null, "-d", "E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\classes\\", javaFile.getCanonicalPath()); //进行动态编译，并返回结果
            System.out.println(result==0?"编译成功":"编译失败");
            generateFile.add("E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\classes\\"+path.replace('.', '\\')+".class");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建.java文件所在路径 和 返回.java文件File对象
     * @param outDirFile 生成文件路径
     * @param javaPackage java包名
     * @param javaClassName java类名
     * @return
     */
    private static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + ".java");
        if(!packagePath.exists()){
            packagePath.mkdirs();
        }
        return file;
    }

    /**
     * 删除生成的文件
     */
    public static void deleteFile(){
        try{
            for(String path:generateFile){
                File file = new File(path);
                if(file.delete()){
                    System.out.println(file.getName() + " 文件已被删除！");
                }else{
                    System.out.println("文件删除失败！");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
