package com.yzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.dao.HDFSConfigDao;
import com.yzx.domain.HDFSConfig;
import com.yzx.service.IHDFSConfigService;
import org.springframework.stereotype.Service;

@Service
public class HDFSConfigServiceImpl extends ServiceImpl<HDFSConfigDao, HDFSConfig> implements IHDFSConfigService {
}
