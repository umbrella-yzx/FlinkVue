package com.yzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.dao.RedisConfigDao;
import com.yzx.domain.RedisConfig;
import com.yzx.service.IRedisConfigService;
import org.springframework.stereotype.Service;

@Service
public class RedisConfigServiceImpl extends ServiceImpl<RedisConfigDao, RedisConfig> implements IRedisConfigService {
}
