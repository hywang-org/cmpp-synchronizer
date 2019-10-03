package com.cmpp.controller;

import com.cmpp.service.SyncService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 作者： chengli
 * 日期： 2019/10/2 14:12
 */
@RestController
@RequestMapping("sync")
public class SyncController {

    @Resource
    private SyncService syncService;

    @RequestMapping("do")
    public String sync() {
        syncService.syncAppInfo2Redis();
        return "success";
    }
}
