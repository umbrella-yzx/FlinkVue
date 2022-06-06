package com.yzx.controller;

import com.yzx.utils.Utils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping
public class GraphJsonController {
    private String entityJson;

    @PostMapping("/graphJson")
    public String graphJson(@RequestBody String json){
        Utils.execute(entityJson,json);
        return json;
    }

    @PostMapping("/entityJson")
    public String entityJson(@RequestBody String json){
        entityJson = json;
        return json;
    }
}
