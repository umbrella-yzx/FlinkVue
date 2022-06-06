package com.yzx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class KafkaConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String topic;
    private String bootstrapServers;
    private String groupId;
}
