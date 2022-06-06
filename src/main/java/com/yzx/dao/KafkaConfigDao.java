package com.yzx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.domain.KafkaConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KafkaConfigDao extends BaseMapper<KafkaConfig> {
}
