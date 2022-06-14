package com.yzx.controller;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@CrossOrigin("*")
@RequestMapping
public class FileController {
    // 设置固定的日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 将 yml 中的自定义配置注入到这里
    @Value("${file.root.path}")
    private String filePath;
    // 日志打印
    private Logger log = LoggerFactory.getLogger("FileController");

    // 文件上传 （可以多文件上传）
    @PostMapping("/userFileUpload")
    public Result fileUploads(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException {
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 得到文件保存的位置以及新文件名
        File dest = new File(filePath + fileName);
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            // 打印日志
            log.info("上传成功，当前上传的文件保存在 {}",filePath + fileName);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            return Result.succ("上传成功");
        } catch (IOException e) {
            log.error(e.toString());
        }
        // 待完成 —— 文件类型校验工作
        return Result.fail("上传错误");
    }

    /**
     * 通用返回结果集
     */
    @Data
    public static class Result {
        private Object data;
        private String msg;
        private int code;

        // getter setter 省略，构造方法省略
        // 操作成功返回数据
        public static Result succ(Object data) {
            return succ(200, "操作成功", data);
        }

        public static Result succ(String msg) {
            return succ(200, msg, null);
        }


        public static Result succ(int code, String msg, Object data) {
            Result r = new Result();
            r.setCode(code);
            r.setMsg(msg);
            r.setData(data);
            return r;
        }

        public static Result succ(String msg, Object data) {
            return succ(200,msg,data);
        }

        // 操作异常返回
        public static Result fail(int code, String msg, Object data) {
            Result r = new Result();
            r.setCode(code);
            r.setMsg(msg);
            r.setData(data);
            return r;
        }

        public static Result fail(String msg) {
            return fail(400,msg,null);
        }

        public static Result fail(int code, String msg) {
            return fail(code,msg,"null");
        }

        public static Result fail(String msg, Object data) {
            return fail(400,msg,data);
        }
    }
}
