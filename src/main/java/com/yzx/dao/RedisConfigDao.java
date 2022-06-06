package com.yzx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.domain.RedisConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedisConfigDao extends BaseMapper<RedisConfig> {
}
