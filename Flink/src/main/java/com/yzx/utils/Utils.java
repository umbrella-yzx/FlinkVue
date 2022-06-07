package com.yzx.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzx.source.config.*;
import com.yzx.template.Node;
import com.yzx.template.Process;
import com.yzx.template.entity.Entity;
import com.yzx.template.entity.Property;
import com.yzx.template.entity.PropertyType;
import com.yzx.template.operate.*;
import com.yzx.template.sink.SinkCSV;
import com.yzx.template.sink.SinkConsle;
import com.yzx.template.sink.SinkJdbc;
import com.yzx.template.source.*;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.map.HashedMap;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Utils {
    //映射用户的字段类型到java的数据类型名称
    public static Map<String,String> field2javaMap = new HashedMap();

    //映射用户字段类型到Property
    public static Map<String, PropertyType> field2typeMap = new HashedMap();

    //映射类名到Entity
    public static Map<String, Entity> name2entityMap = new HashedMap();

    //映射节点名到节点
    public static Map<String, Node> name2nodeMap = new HashedMap();

    //映射java数据类型到MySQL数据类型
    public static Map<String, String> java2sqlMap = new HashedMap();

    private static List<String> className = new ArrayList<>();

    static {
        field2javaMap.put("byte","byte");
        field2javaMap.put("short","short");
        field2javaMap.put("int","int");
        field2javaMap.put("long","long");
        field2javaMap.put("boolean","boolean");
        field2javaMap.put("float","float");
        field2javaMap.put("double","double");
        field2javaMap.put("string","String");
        field2javaMap.put("bytearray","ByteArray");
        field2javaMap.put("date","Date");
        field2javaMap.put("list","List");

        field2typeMap.put("byte",PropertyType.Byte);
        field2typeMap.put("short",PropertyType.Short);
        field2typeMap.put("int",PropertyType.Int);
        field2typeMap.put("long",PropertyType.Long);
        field2typeMap.put("boolean",PropertyType.Boolean);
        field2typeMap.put("float",PropertyType.Float);
        field2typeMap.put("double",PropertyType.Double);
        field2typeMap.put("string",PropertyType.String);
        field2typeMap.put("bytearray",PropertyType.ByteArray);
        field2typeMap.put("date",PropertyType.Date);
        field2typeMap.put("list",PropertyType.List);

        java2sqlMap.put("byte","TINYINT");
        java2sqlMap.put("short","INT");
        java2sqlMap.put("int","INT");
        java2sqlMap.put("long","BIGINT");
        java2sqlMap.put("boolean","BOOLEAN");
        java2sqlMap.put("float","FLOAT");
        java2sqlMap.put("double","DOUBLE");
        java2sqlMap.put("string","STRING");
        java2sqlMap.put("date","DATE");
        java2sqlMap.put("list","ARRAY");
    }

    private static File javaFile = null;

    public static ArrayList<String> generateFile = new ArrayList<>();

    public static void execute(String entityJson,String json){
        //当前 Java 应用程序相关的运行时对象
        Runtime run=Runtime.getRuntime();
        //程序结束时删除所有生成的文件
        run.addShutdownHook(new Thread(Utils::deleteFile));

        Utils.generateEntity(entityJson);

        Utils.generateProcess(json);

        try{
            //通过反射方法动态执行
            //1、首先构建文件的目录url地址，
            URL[] urls =new URL[] {new URL("file:E:/Java/Workspace/FlinkVue/Flink/src/main/java/com/yzx/process/")};
            //2、使用URLClassLoader对象的loadClass方法加载对应类
            URLClassLoader loder=new URLClassLoader(urls);
            //3、获取所加载类的方法
            Class<?> clazz =loder.loadClass("com.yzx.process.Process");
            // 4、传入方法所需的参数通过invoke运行方法
            Method method=clazz.getDeclaredMethod("execute");
            method.invoke(null); //当类型为String[]时，需要(Object)new String[] {}初始化
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

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
                //SinkJDBC
                case 6:{
                    javaFile = toJavaFilename(outDirFile, ((SinkJdbc)obj).javaPackage, ((SinkJdbc)obj).className);
                    path = ((SinkJdbc)obj).getJavaPackage()+"."+((SinkJdbc)obj).getClassName();
                    root.put("jdbcsink", ((SinkJdbc)obj));
                    break;
                }
                //Reduce
                case 7:{
                    javaFile = toJavaFilename(outDirFile, ((OperateReduce)obj).javaPackage, ((OperateReduce)obj).className);
                    path = ((OperateReduce)obj).getJavaPackage()+"."+((OperateReduce)obj).getClassName();
                    root.put("reduce", ((OperateReduce)obj));
                    break;
                }
                //keyBy
                case 8:{
                    javaFile = toJavaFilename(outDirFile, ((OperateKeySelect)obj).javaPackage, ((OperateKeySelect)obj).className);
                    path = ((OperateKeySelect)obj).getJavaPackage()+"."+((OperateKeySelect)obj).getClassName();
                    root.put("keyby", ((OperateKeySelect)obj));
                    break;
                }
                //FlatMap
                case 9:{
                    javaFile = toJavaFilename(outDirFile, ((OperateFlatMap)obj).javaPackage, ((OperateFlatMap)obj).className);
                    path = ((OperateFlatMap)obj).getJavaPackage()+"."+((OperateFlatMap)obj).getClassName();
                    root.put("flatmap", ((OperateFlatMap)obj));
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

    /**
     * 根据json字符串生成Entity
     * @param json
     */
    public static void generateEntity(String json){
        JSONArray objects = JSONObject.parseArray(json);
        for(int i=0;i<objects.size();++i) {
            JSONObject object = JSONObject.parseObject(objects.get(i).toString());
            Entity entity = new Entity();
            entity.setClassName(object.getString("name"));
            entity.setJavaPackage("com.yzx.entity");

            // 创建实体属性
            List<Property> propertyList = new ArrayList<Property>();
            JSONArray fields = object.getJSONArray("ziduan");
            for (int j = 0; j < fields.size(); ++j) {
                JSONObject field = fields.getJSONObject(j);
                Property attribute = new Property();
                attribute.setJavaType(field2javaMap.get(field.getString("type").toLowerCase(Locale.ROOT)));
                attribute.setPropertyName(field.getString("name"));
                attribute.setPropertyType(field2typeMap.get(field.getString("type").toLowerCase(Locale.ROOT)));
                propertyList.add(attribute);
            }
            entity.setProperties(propertyList);
            entity.setConstructors(true);
//            System.out.println(entity.toString());
            name2entityMap.put(entity.getClassName(), entity);
            generateCode(entity,0,"entity.ftl");
        }
    }

    /**
     * 随机生成字符串
     * @param prefix 字符串前缀
     * @return
     */
    private static String getRandomString(String prefix){
        int length = 5;
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String res;
        Random random=new Random();
        do{
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<length;i++){
                int number=random.nextInt(str.length());
                sb.append(str.charAt(number));
            }
            res = prefix + sb.toString();
        }while(className.contains(res));
        className.add(res);
        return res;
    }

    /**
     * 拓扑排序类
     */
    private static class Graph{
        int V;  //顶点个数
        List<Integer>[] adj;    //邻接表
        Queue<Integer> q = new PriorityQueue<>();
        int[] indegree;

        public Graph(int v) {
            V = v;
            indegree = new int[V];
            adj = new List[V];

            for(int i=0;i<V;++i){
                indegree[i]=0;
                adj[i] = new ArrayList<>();
            }
        }

        public void addEdge(int v,int w){
            adj[v].add(w);
            ++indegree[w];
        }

        public List<Integer> topologicalSort(){
            List<Integer> resList = new ArrayList<>();
            for(int i=0; i<V; ++i)
                if(indegree[i] == 0)
                    q.add(i);         // 将所有入度为0的顶点入队

            int count = 0;             // 计数，记录当前已经输出的顶点数
            while(!q.isEmpty())
            {
                int v = q.poll();      // 从队列中取出一个顶点
                resList.add(v);  // 存入该顶点
                ++count;
                // 将所有v指向的顶点的入度减1，并将入度减为0的顶点入栈
                List<Integer> list = adj[v];
                for(int i=0;i<list.size();++i){
                    if(--indegree[list.get(i)]<=0){
                        q.add(list.get(i));     // 若入度为0，则入栈
                    }
                }
            }
            return resList;
        }
    }

    /**
     * 生成process对象
     * @param json 前端传来的数据流图json字符串
     * @return
     */
    public static Process generateProcess(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray nodeList = jsonObject.getJSONArray("nodeList");
        JSONArray lineList = jsonObject.getJSONArray("lineList");

        //对于union节点，保存节点名字和对应的前置变量名集合
        Map<String,List<String>> unionName2PreNames = new HashedMap();

        List<String> idx2name = new ArrayList<>();
        for(int i=0;i< nodeList.size();++i){
            JSONObject object = nodeList.getJSONObject(i);
            idx2name.add(object.getString("id"));
            switch (object.getString("type")){
                case "dataResource":Utils.name2nodeMap.put(object.getString("id"),generateSourceNode(object));break;
                case "dataResult":Utils.name2nodeMap.put(object.getString("id"),generateSinkNode(object));break;
                case "Aggregation":Utils.name2nodeMap.put(object.getString("id"),generateOperateAggregation(object));break;
                case "Reduce":Utils.name2nodeMap.put(object.getString("id"),generateOperateReduce(object));break;
                case "Filter":Utils.name2nodeMap.put(object.getString("id"),generateOperateFilter(object));break;
                case "Map":Utils.name2nodeMap.put(object.getString("id"),generateOperateMap(object));break;
                case "FlatMap":Utils.name2nodeMap.put(object.getString("id"),generateOperateFlatMap(object));break;
                case "KeyBy":Utils.name2nodeMap.put(object.getString("id"),generateOperateKeySelect(object));break;
                case "Union":{
                    String id = object.getString("id");
                    Utils.name2nodeMap.put(id,generateOperateUnion(object));
                    unionName2PreNames.put(id,new ArrayList<>());
                }break;
                default:break;
            }
        }
        Graph g = new Graph(nodeList.size());   // 创建图
        for (int i = 0; i < lineList.size(); i++) {
            JSONObject object = lineList.getJSONObject(i);
            g.addEdge(idx2name.indexOf(object.getString("from")),idx2name.indexOf(object.getString("to")));

            //设置前置变量名，Union需要特殊操作
            if(unionName2PreNames.containsKey(object.getString("to"))){ //如果是union节点
                unionName2PreNames.get(object.getString("to")).add(object.getString("from"));
            }else{
                Utils.name2nodeMap.get(object.getString("to")).setPreName(object.getString("from"));
            }
        }
        //设置union节点的前置变量名和条件
        for(String key:unionName2PreNames.keySet()){
            ((OperateUnion) Utils.name2nodeMap.get(key)).setConditionAndPreName(unionName2PreNames.get(key));
        }

        List<Integer> list = g.topologicalSort();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            //根据拓扑排序结果生成代码
            nodes.add(Utils.name2nodeMap.get(idx2name.get(list.get(i))));
        }

        Process process = new Process();
        process.setNodes(nodes);

        //生成代码
        Utils.generateCode(process,4,"process.ftl");

        return process;
    }

    private static Node generateSinkNode(JSONObject object){
        switch (object.getString("dataResultType")){
            case "window":return generateSinkConsle(object);
            case "MySql":return generateSinkJdbc(object);
            case "CSV":return generateSinkCSV(object);
            default:return generateSinkConsle(object);
        }
    }


    /**
     * 生成数据源节点
     * @param object JSONObjectduix
     * @param <T> 指定的数据源泛型
     * @return 返回不同的数据源节点
     */
    private static Node generateSourceNode(JSONObject object){
        switch (object.getString("state")){
            case "JDBC":return generateSourceJdbc(object);
            case "CSV":return generateSourceCSV(object);
            case "HDFS":return generateSourceHDFS(object);
            case "Redis":return generateSourceRedis(object);
            case "Kafka":return generateSourceKafka(object);
            default:return null;
        }
    }

    /**
     * 生成JDBC数据源节点对象
     * @param object
     * @return
     */
    private static SourceJdbc generateSourceJdbc(JSONObject object){
        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setUrl(object.getString("jdbc_DBUrl"));
        jdbcConfig.setUserName(object.getString("jdbc_userName"));
        jdbcConfig.setPassword(object.getString("jdbc_password"));
        jdbcConfig.setTableName(object.getString("jdbc_tableName"));
        jdbcConfig.setExConfig(object.getString("jdbc_exConfig"));

        SourceJdbc sourceJdbc = new SourceJdbc();
        sourceJdbc.curName = object.getString("id");
        sourceJdbc.setJdbcConfig(jdbcConfig);
        List<String> inPackage = new ArrayList<>();
        inPackage.add("com.yzx.entity."+object.getString("dataResourceDingyi"));
        sourceJdbc.setInPackages(inPackage);
        sourceJdbc.setEntity(Utils.name2entityMap.get(object.getString("dataResourceDingyi")));
        sourceJdbc.setClassName(Utils.getRandomString(sourceJdbc.getType()));

        //生成代码
        Utils.generateCode(sourceJdbc,1,"source_jdbc.ftl");

        return sourceJdbc;
    }

    /**
     * 生成CSV数据源节点
     * @param object
     * @return
     */
    private static SourceCSV generateSourceCSV(JSONObject object){
        CSVConfig csvConfig = new CSVConfig();
        csvConfig.setPath(object.getString("csv_path"));
        csvConfig.setExConfig(object.getString("csv_exConfig"));

        SourceCSV sourceCSV = new SourceCSV();
        sourceCSV.setCsvConfig(csvConfig);
        Entity entity = Utils.name2entityMap.get(object.getString("dataResourceDingyi"));
        sourceCSV.setEntity(entity);
        List<String> inPackage = new ArrayList<>();
        inPackage.add("com.yzx.entity."+object.getString("dataResourceDingyi"));
        sourceCSV.setInPackages(inPackage);
        sourceCSV.setClassName(Utils.getRandomString(sourceCSV.getType()));
        sourceCSV.curName = object.getString("id");

        //根据Entity生成Sql语句
        List<Property> properties = entity.getProperties();
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE mytable(");
        for (int i = 0; i < properties.size(); i++) {
            sb.append(" `"+properties.get(i).getPropertyName()+"` "+Utils.java2sqlMap.get(properties.get(i).getJavaType().toLowerCase(Locale.ROOT)));
            if(i!= properties.size()-1)sb.append(",");
        }
        sb.append(")WITH("+
                " 'connector' = 'filesystem',"+
                " 'path' = '"+csvConfig.getPath()+"',"+
                " 'format' = 'csv'"+
                ")");

        sourceCSV.setSql(sb.toString());

        //生成代码
        Utils.generateCode(sourceCSV,5,"source_csv.ftl");

        return sourceCSV;
    }

    /**
     * 生成HDFS数据源节点
     * @param object
     * @return
     */
    private static SourceHDFS generateSourceHDFS(JSONObject object) {
        HDFSConfig hdfsConfig = new HDFSConfig();
        hdfsConfig.setHost(object.getString("hdfs_host"));
        hdfsConfig.setPort(Integer.parseInt(object.getString("hdfs_port")));
        hdfsConfig.setFilePath(object.getString("hdfs_filePath"));

        SourceHDFS sourceHDFS = new SourceHDFS();
        sourceHDFS.setHdfsConfig(hdfsConfig);
        sourceHDFS.curName = object.getString("id");

        return sourceHDFS;
    }

    /**
     * 生成Kafka数据源节点
     * @param object
     * @return
     */
    private static SourceKafka generateSourceKafka(JSONObject object) {
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaConfig.setTopic(object.getString("kafka_topic"));
        List<KafkaConfig.MyProperty> myProperties = new ArrayList<>();
        myProperties.add(new KafkaConfig.MyProperty("bootstrap.servers",object.getString("kafka_bootstrapServers")));
        myProperties.add(new KafkaConfig.MyProperty("group.id",object.getString("kafka_groupId")));
        kafkaConfig.setProperties(myProperties);

        SourceKafka sourceKafka = new SourceKafka();
        sourceKafka.setKafkaConfig(kafkaConfig);
        sourceKafka.curName = object.getString("id");

        return sourceKafka;
    }

    /**
     * 生成redis数据源节点
     * @param object
     * @return
     */
    private static SourceRedis generateSourceRedis(JSONObject object) {
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setHost(object.getString("redis_host"));
        redisConfig.setPort(Integer.parseInt(object.getString("redis_port")));
        if(object.getString("redis_password")==null||object.getString("redis_password").equals("")){
            redisConfig.setPassword("null");
        }else{
            redisConfig.setPassword("\""+object.getString("redis_password")+"\"");
        }
        redisConfig.setKey(object.getString("redis_key"));
        redisConfig.setCommand(object.getString("redis_command"));

        SourceRedis sourceRedis = new SourceRedis(redisConfig);
        sourceRedis.curName = object.getString("id");

        return sourceRedis;
    }

    /**
     * 生成滚动聚合操作节点
     * @param object
     * @return
     */
    private static OperateAggregation generateOperateAggregation(JSONObject object) {
        OperateAggregation operateAggregation = new OperateAggregation();
        switch (object.getString("Aggregation_leixing")){
            case "sum":operateAggregation.setAggregationType(0);break;
            case "min":operateAggregation.setAggregationType(1);break;
            case "max":operateAggregation.setAggregationType(2);break;
            case "minBy":operateAggregation.setAggregationType(3);break;
            case "maxBy":operateAggregation.setAggregationType(4);break;
            default:break;
        }
        operateAggregation.setCondition(object.getString("Aggregation_tiaojian"));
        operateAggregation.setOutClass(object.getString("Aggregation_inLei"));
        operateAggregation.curName = object.getString("id");

        return operateAggregation;
    }

    private static OperateFilter generateOperateFilter(JSONObject object){
        OperateFilter filter = new OperateFilter();
        filter.setCondition(object.getString("Filter_tiaojian"));
        filter.setOutClass(object.getString("Filter_inLei"));
        if(Utils.name2entityMap.containsKey(object.getString("Filter_inLei"))){
            List<String> inPackage = new ArrayList<>();
            inPackage.add("com.yzx.entity."+object.getString("Filter_inLei"));
            filter.setInPackages(inPackage);
        }
        filter.setClassName(Utils.getRandomString(filter.getType()));
        filter.curName = object.getString("id");

        //生成代码
        Utils.generateCode(filter,2,"operator_filter.ftl");

        return filter;
    }

    private static OperateFlatMap generateOperateFlatMap(JSONObject object) {
        OperateFlatMap flatMap = new OperateFlatMap();
        flatMap.setInClass(object.getString("FlatMap_inLei"));
        flatMap.setOutClass(object.getString("FlatMap_outLei"));
        List<String> inPackage = new ArrayList<>();
        if(Utils.name2entityMap.containsKey(object.getString("FlatMap_inLei"))){
            inPackage.add("com.yzx.entity."+object.getString("FlatMap_inLei"));
        }
        if(Utils.name2entityMap.containsKey(object.getString("FlatMap_outLei"))){
            inPackage.add("com.yzx.entity."+object.getString("FlatMap_outLei"));
        }
        flatMap.setCondition(object.getString("FlatMap_tiaojian"));
        flatMap.setInPackages(inPackage);
        flatMap.setClassName(Utils.getRandomString(flatMap.getType()));
        flatMap.curName = object.getString("id");

        //生成代码
        Utils.generateCode(flatMap,9,"operator_flatmap.ftl");

        return flatMap;
    }

    private static OperateKeySelect generateOperateKeySelect(JSONObject object) {
        OperateKeySelect operateKeySelect = new OperateKeySelect();
        operateKeySelect.setInClass(object.getString("KeyBy_inLei"));
        operateKeySelect.setOutClass(object.getString("KeyBy_outLei"));
        List<String> inPackage = new ArrayList<>();
        if(Utils.name2entityMap.containsKey(object.getString("KeyBy_inLei"))){
            inPackage.add("com.yzx.entity."+object.getString("KeyBy_inLei"));
        }
        if(Utils.name2entityMap.containsKey(object.getString("KeyBy_outLei"))){
            inPackage.add("com.yzx.entity."+object.getString("KeyBy_outLei"));
        }
        operateKeySelect.setCondition(object.getString("KeyBy_tiaojian"));
        operateKeySelect.setInPackages(inPackage);
        operateKeySelect.setClassName(Utils.getRandomString(operateKeySelect.getType()));
        operateKeySelect.curName = object.getString("id");

        //生成代码
        Utils.generateCode(operateKeySelect,8,"operator_keyby.ftl");

        return operateKeySelect;
    }

    private static OperateMap generateOperateMap(JSONObject object) {
        OperateMap map = new OperateMap();
        map.setInClass(object.getString("Map_inLei"));
        map.setOutClass(object.getString("Map_outLei"));
        List<String> inPackage = new ArrayList<>();
        if(Utils.name2entityMap.containsKey(object.getString("Map_inLei"))){
            inPackage.add("com.yzx.entity."+object.getString("Map_inLei"));
        }
        if(Utils.name2entityMap.containsKey(object.getString("Map_outLei"))){
            inPackage.add("com.yzx.entity."+object.getString("Map_outLei"));
        }
        map.setCondition(object.getString("Map_tiaojian"));
        map.setInPackages(inPackage);
        map.setClassName(Utils.getRandomString(map.getType()));
        map.curName = object.getString("id");

        //生成代码
        Utils.generateCode(map,3,"operator_map.ftl");

        return map;
    }

    private static OperateReduce generateOperateReduce(JSONObject object) {
        OperateReduce reduce = new OperateReduce();
        reduce.setOutClass(object.getString("Reduce_inLei"));
        List<String> inPackage = new ArrayList<>();
        if(Utils.name2entityMap.containsKey(object.getString("Reduce_inLei"))){
            inPackage.add("com.yzx.entity."+object.getString("Reduce_inLei"));
        }
        reduce.setCondition(object.getString("Reduce_tiaojian"));
        reduce.setInPackages(inPackage);
        reduce.setClassName(Utils.getRandomString(reduce.getType()));
        reduce.curName = object.getString("id");

        //生成代码
        Utils.generateCode(reduce,7,"operator_reduce.ftl");

        return reduce;
    }

    private static OperateUnion generateOperateUnion(JSONObject object) {
        OperateUnion union = new OperateUnion();
        union.curName = object.getString("id");

        return union;
    }

    private static SinkConsle generateSinkConsle(JSONObject object){
        SinkConsle sinkConsle = new SinkConsle();
        return sinkConsle;
    }

    private static SinkJdbc generateSinkJdbc(JSONObject object){
        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setUrl(object.getString("dataResultMySql_url"));
        jdbcConfig.setUserName(object.getString("dataResultMySql_userName"));
        jdbcConfig.setPassword(object.getString("dataResultMySql_password"));
        jdbcConfig.setTableName(object.getString("dataResultMySql_tableName"));
        jdbcConfig.setExConfig(object.getString("dataResultMySql_exConfig"));

        SinkJdbc sinkJdbc = new SinkJdbc();
        sinkJdbc.curName = object.getString("id");
        sinkJdbc.setJdbcConfig(jdbcConfig);
        List<String> inPackage = new ArrayList<>();
        inPackage.add("com.yzx.entity."+object.getString("dataResultMySql_dataPeizhi"));
        sinkJdbc.setInPackages(inPackage);
        sinkJdbc.setEntity(Utils.name2entityMap.get(object.getString("dataResultMySql_dataPeizhi")));
        sinkJdbc.setClassName(Utils.getRandomString(sinkJdbc.getType()));

        //生成代码
        Utils.generateCode(sinkJdbc,6,"sink_jdbc.ftl");

        return sinkJdbc;
    }

    private static SinkCSV generateSinkCSV(JSONObject object){
        CSVConfig csvConfig = new CSVConfig();
        csvConfig.setPath(object.getString("dataResultCSV_path"));
        csvConfig.setExConfig(object.getString("dataResultCSV_exConfig"));

        SinkCSV sinkCSV = new SinkCSV();
        sinkCSV.setCsvConfig(csvConfig);
        sinkCSV.setOutClass(object.getString("dataResultCSV_outLei"));
        if(Utils.name2entityMap.containsKey(object.getString("dataResultCSV_outLei")))sinkCSV.setIsentity(true);

        return sinkCSV;
    }
}
