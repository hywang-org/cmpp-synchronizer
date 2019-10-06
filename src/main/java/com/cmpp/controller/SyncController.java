package com.cmpp.controller;

import com.cmpp.common.redis.AppInfoRedis;
import com.cmpp.service.SyncService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

/**
 * 作者： chengli
 * 日期： 2019/10/2 14:12
 */
@RestController
@RequestMapping("sync")
public class SyncController {

    @Resource
    private SyncService syncService;
    @Resource
    AppInfoRedis r3;

    @RequestMapping("do")
    public String sync() {
        syncService.syncAppInfoToRedis();
        syncService.syncChannelToRedis();
        return "success";
    }

    /**
     * 扣量
     *
     * @return
     */
    @RequestMapping("deduction")
    public String deduction() {
        String deduction = loadFileContent("lua/deduction.lua");
        boolean result = r3.eval(deduction, Collections.singletonList("appId01"), "1");
        System.out.println(result);
        return "success";
    }

    /**
     * 连接数限制
     *
     * @return
     */
    @RequestMapping("getConn")
    public String getConn() {
        String connection = loadFileContent("lua/connection.lua");
        boolean result = r3.eval(connection, Collections.singletonList("appId01"), "1");
        System.out.println(result);
        return "success";
    }

    private String loadFileContent(String resourcePath) {
        try {
            File file = new ClassPathResource(resourcePath).getFile();
            Long fileLength = file.length();
            byte[] b = new byte[fileLength.intValue()];
            try (FileInputStream in = new FileInputStream(file)) {
                int length = in.read(b, 0, b.length);
                if (length == b.length || length == -1) { // 已经读满或者已经到了流的结尾
                    return new String(b, StandardCharsets.UTF_8);
                }
                int temp;
                while ((temp = in.read(b, length, b.length - length)) != -1) {
                    length += temp;
                    if (length == b.length) {
                        return new String(b, StandardCharsets.UTF_8);
                    }
                }
                return new String(b, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            return null;
        }
    }
}
