package com.yzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzx.dao.KafkaConfigDao;
import com.yzx.domain.KafkaConfig;
import com.yzx.service.IKafkaConfigService;
import org.springframework.stereotype.Service;

@Service
public class KafkaConfigServiceImpl extends ServiceImpl<KafkaConfigDao, KafkaConfig> implements IKafkaConfigService {
}
