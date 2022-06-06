package com.yzx.controller;

import com.yzx.domain.JdbcConfig;
import com.yzx.service.IJdbcConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private IJdbcConfigService jdbcConfigService;

    @GetMapping("/jdbc")
    public List<JdbcConfig> getAll(){
        return jdbcConfigService.list();
    }

    @PostMapping
    public Boolean save(JdbcConfig jdbcConfig){
        return jdbcConfigService.save(jdbcConfig);
    }
}
