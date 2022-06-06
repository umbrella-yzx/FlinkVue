package com.yzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.dao.CSVConfigDao;
import com.yzx.domain.CSVConfig;
import com.yzx.service.ICSVConfigService;
import org.springframework.stereotype.Service;

@Service
public class CSVConfigServiceImpl extends ServiceImpl<CSVConfigDao, CSVConfig> implements ICSVConfigService {
}
