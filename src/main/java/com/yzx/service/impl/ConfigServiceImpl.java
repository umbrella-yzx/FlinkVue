package com.yzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.dao.ConfigDao;
import com.yzx.domain.Config;
import com.yzx.service.IConfigService;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, Config> implements IConfigService {
}
