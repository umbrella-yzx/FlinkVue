package com.yzx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.domain.Config;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigDao extends BaseMapper<Config> {
}
