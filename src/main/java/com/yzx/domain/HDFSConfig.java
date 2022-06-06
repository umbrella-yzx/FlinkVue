package com.yzx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class HDFSConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String host;
    private Integer port;
    private String filePath;
}
