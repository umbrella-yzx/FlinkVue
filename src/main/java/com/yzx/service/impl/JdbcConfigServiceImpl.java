package com.yzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.dao.JdbcConfigDao;
import com.yzx.domain.JdbcConfig;
import com.yzx.service.IJdbcConfigService;
import org.springframework.stereotype.Service;

@Service
public class JdbcConfigServiceImpl extends ServiceImpl<JdbcConfigDao, JdbcConfig> implements IJdbcConfigService {
}
