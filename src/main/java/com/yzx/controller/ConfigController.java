package com.yzx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzx.domain.*;
import com.yzx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private IJdbcConfigService jdbcConfigService;

    @Autowired
    private ICSVConfigService csvConfigService;

    @Autowired
    private IRedisConfigService redisConfigService;

    @Autowired
    private IHDFSConfigService hdfsConfigService;

    @Autowired
    private IKafkaConfigService kafkaConfigService;

    @GetMapping("/jdbc")
    public List<JdbcConfig> getJdbcAll(){
        return jdbcConfigService.list();
    }

    @PostMapping("/jdbc/json")
    public Boolean saveJdbc(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        JdbcConfig jdbcConfig = new JdbcConfig();

        jdbcConfig.setDBUrl(object.getString("jdbc_DBUrl"));
        jdbcConfig.setUserName(object.getString("jdbc_userName"));
        jdbcConfig.setTableName(object.getString("jdbc_tableName"));
        jdbcConfig.setPassword(object.getString("jdbc_password"));
        jdbcConfig.setExConfig(object.getString("jdbc_exConfig"));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            jdbcConfig.setId(Integer.parseInt(field_id));
            return jdbcConfigService.updateById(jdbcConfig);
        }else {
            return jdbcConfigService.save(jdbcConfig);
        }
    }

    @PostMapping("/jdbcResult/json")
    public Boolean saveResultJdbc(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        JdbcConfig jdbcConfig = new JdbcConfig();

        jdbcConfig.setDBUrl(object.getString("dataResultMySql_url"));
        jdbcConfig.setUserName(object.getString("dataResultMySql_userName"));
        jdbcConfig.setTableName(object.getString("dataResultMySql_tableName"));
        jdbcConfig.setPassword(object.getString("dataResultMySql_password"));
        jdbcConfig.setExConfig(object.getString("dataResultMySql_exConfig"));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            jdbcConfig.setId(Integer.parseInt(field_id));
            return jdbcConfigService.updateById(jdbcConfig);
        }else {
            return jdbcConfigService.save(jdbcConfig);
        }
    }

    @GetMapping("/csv")
    public List<CSVConfig> getCsvAll(){
        return csvConfigService.list();
    }

    @PostMapping("/csv/json")
    public Boolean saveCsv(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        int age = 10;

        CSVConfig csvConfig = new CSVConfig();

        csvConfig.setPath(object.getString("csv_path"));
        csvConfig.setExConfig(object.getString("csv_exConfig"));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            csvConfig.setId(Integer.parseInt(field_id));
            return csvConfigService.updateById(csvConfig);
        }else {
            return csvConfigService.save(csvConfig);
        }
    }

    @PostMapping("/csvResult/json")
    public Boolean saveResultCsv(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        CSVConfig csvConfig = new CSVConfig();

        csvConfig.setPath(object.getString("dataResultCSV_path"));
        csvConfig.setExConfig(object.getString("dataResultCSV_exConfig"));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            csvConfig.setId(Integer.parseInt(field_id));
            return csvConfigService.updateById(csvConfig);
        }else {
            return csvConfigService.save(csvConfig);
        }
    }

    @GetMapping("/redis")
    public List<RedisConfig> getRedisAll(){
        return redisConfigService.list();
    }

    @PostMapping("/redis/json")
    public Boolean saveRedis(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        RedisConfig redisConfig = new RedisConfig();

        redisConfig.setHost(object.getString("redis_host"));
        redisConfig.setPassword(object.getString("redis_password"));
        redisConfig.setPort(Integer.parseInt(object.getString("redis_port")));
        redisConfig.setCommand(object.getString("redis_command"));
        redisConfig.setExConfig(object.getString("redis_exConfig"));
        redisConfig.setRedisKey(object.getString("redis_key"));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            redisConfig.setId(Integer.parseInt(field_id));
            return redisConfigService.updateById(redisConfig);
        }else {
            return redisConfigService.save(redisConfig);
        }
    }

    @GetMapping("/hdfs")
    public List<HDFSConfig> geHdfsAll(){
        return hdfsConfigService.list();
    }

    @PostMapping("/hdfs/json")
    public Boolean saveHDFS(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        HDFSConfig hdfsConfig = new HDFSConfig();

        hdfsConfig.setHost(object.getString("hdfs_host"));
        hdfsConfig.setFilePath(object.getString("hdfs_filePath"));
        hdfsConfig.setPort(Integer.parseInt(object.getString("hdfs_port")));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            hdfsConfig.setId(Integer.parseInt(field_id));
            return hdfsConfigService.updateById(hdfsConfig);
        }else {
            return hdfsConfigService.save(hdfsConfig);
        }
    }

    @GetMapping("/kafka")
    public List<KafkaConfig> getKafkaAll(){
        return kafkaConfigService.list();
    }

    @PostMapping("/kafka/json")
    public Boolean saveKafka(@RequestBody String json){
        JSONObject object = JSONObject.parseObject(json);

        KafkaConfig kafkaConfig = new KafkaConfig();

        kafkaConfig.setGroupId(object.getString("kafka_groupId"));
        kafkaConfig.setBootstrapServers(object.getString("kafka_bootstrapServers"));
        kafkaConfig.setTopic(object.getString("kafka_topic"));

        String field_id = object.getString("field_id");
        if(field_id !=null&&!field_id.equals("")){
            kafkaConfig.setId(Integer.parseInt(field_id));
            return kafkaConfigService.updateById(kafkaConfig);
        }else {
            return kafkaConfigService.save(kafkaConfig);
        }
    }

}
