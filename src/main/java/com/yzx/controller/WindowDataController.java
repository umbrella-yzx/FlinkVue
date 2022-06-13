package com.yzx.controller;

import com.yzx.data.SharedData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping
public class WindowDataController {
    @GetMapping("/Data")
    public static String getData(){
        return SharedData.sb.toString()+'\n';
    }

//
//    @GetMapping("/stopWindowData")
//    public void stopConsoleMessage() {
//        isRunning = false;
//    }
}
