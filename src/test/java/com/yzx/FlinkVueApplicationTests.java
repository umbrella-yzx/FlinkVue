package com.yzx;

import com.yzx.dao.JdbcConfigDao;
import com.yzx.domain.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlinkVueApplicationTests {

    @Autowired
    private JdbcConfigDao jdbcConfigDao;

    @Test
    void contextLoads() {
        jdbcConfigDao.insert(new JdbcConfig("jdbc:mysql://localhost:3306/test", "student", "root", "root"));
    }

}
