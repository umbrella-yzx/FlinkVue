package com.yzx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RedisConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String host;    //主机
    private Integer port;   //端口
    private String password;    //密码
    private String exConfig;    //其余配置
    private String command; //读取redis的命令
    private String redisKey;     //读取的key

    public RedisConfig() {
    }
}
