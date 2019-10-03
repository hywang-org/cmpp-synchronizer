package com.cmpp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmpp.common.JsonBuilder;
import com.cmpp.common.redis.AppInfoRedis;
import com.cmpp.entity.App;
import com.cmpp.entity.Module;
import com.cmpp.entity.ProSecret;
import com.cmpp.service.dao.SyncDao;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.cmpp.common.redis.RedisConsts.*;

/**
 * 作者： chengli
 * 日期： 2019/10/2 11:34
 */
@Service
public class SyncService {

    @Resource
    private SyncDao syncDao;
    @Resource
    private AppInfoRedis appInfoRedis;

    /**
     * 最新的一条数据的更新时间
     */
    private Date newestAppUpdateTime = null;
    private Date newestModuleUpdateTime = null;
    private Date newestConsumptionUpdateTime = null;
    private Date newestSecretUpdateTime = null;

    private Date tempAppUpdateTime = null;
    private Date tempModuleUpdateTime = null;
    private Date tempConsumptionUpdateTime = null;
    private Date tempSecretUpdateTime = null;


    public void syncAppInfo2Redis() {
        this.syncApps();
        this.syncModules();
        this.syncSecret();
        newestSecretUpdateTime = tempSecretUpdateTime;
        newestModuleUpdateTime = tempModuleUpdateTime;
        newestAppUpdateTime = tempAppUpdateTime;
    }

    /**
     * 同步app信息到redis
     */
    private void syncApps() {
        List<App> apps = selectNewApps();
        if (CollectionUtils.isEmpty(apps)) {
            return;
        }
        for (App app : apps) {
            Map<String, String> map = new HashMap<>();
            if (newestAppUpdateTime == null || newestAppUpdateTime.before(app.getUpdatedDate())) {
                tempAppUpdateTime = app.getUpdatedDate();
            }
            map.put(USER_ID, app.getUserId());
            map.put(PROTOCOL_TYPE, String.valueOf(app.getProtocolType()));
            map.put(CALLBACK_URL, app.getCallbackUrl());
            map.put(APP_STATUS, String.valueOf(app.getAppStatus()));
            map.put(EXTEND_CODE, app.getExtendcode());
            map.put(SPEED_LIMIT, String.valueOf(app.getSpeedLimit()));
            map.put(SEND_BEGIN_TIME, String.valueOf(app.getSendBeginTime()));
            map.put(SEND_END_TIME, String.valueOf(app.getSendEndTime()));
            map.put(CHANNEL, app.getChannel());
            appInfoRedis.hset(app.getAppId(), map);
        }
    }

    /**
     * 同步module信息到redis
     */
    private void syncModules() {
        List<Module> modules = selectModules();
        if (CollectionUtils.isEmpty(modules)) {
            return;
        }
        for (Module module : modules) {
            if (newestModuleUpdateTime == null || newestModuleUpdateTime.before(module.getUpdatedDate())) {
                tempModuleUpdateTime = module.getUpdatedDate();
            }
        }
        Map<String, List<Module>> appId2Modules = modules.stream().collect(Collectors.groupingBy(Module::getAppId));
        for (Map.Entry<String, List<Module>> appId2Module : appId2Modules.entrySet()) {
            List<Object> moduleJsonList = new ArrayList<>();
            List<Module> tempModules = appId2Module.getValue();
            Map<String, String> map = new HashMap<>(16);
            for (Module tempModule : tempModules) {
                JSONObject moduleJson = JsonBuilder.create()
                        .json(MODULE_CONTENT, tempModule.getContent())
                        .json(MODULE_STATUS, tempModule.getModStatus())
                        .build();
                moduleJsonList.add(moduleJson);

            }
            map.put(MODULE_ID, JSON.toJSONString(moduleJsonList));
            appInfoRedis.hset(appId2Module.getKey(), map);
        }
    }

    private void syncSecret() {
        List<ProSecret> proSecrets = this.selectProSecrets();
        if (CollectionUtils.isEmpty(proSecrets)) {
            return;
        }
        for (ProSecret proSecret : proSecrets) {
            if (newestSecretUpdateTime == null || newestSecretUpdateTime.before(proSecret.getUpdatedDate())) {
                tempSecretUpdateTime = proSecret.getUpdatedDate();
            }
            appInfoRedis.hset(proSecret.getAppId(), "app_secret", proSecret.getAppSecret());
        }
    }

    private List<ProSecret> selectProSecrets() {
        if (newestSecretUpdateTime == null) {
            return syncDao.find("from ProSecret");
        }
        return syncDao.find("from ProSecret where updatedDate>?", newestSecretUpdateTime);
    }

    /**
     * 同步重置信息到redis
     */
 /*   private void syncConsumption() {
        if (newestConsumptionUpdateTime == null) {
            return syncDao.find("from App");
        }
        return syncDao.find("from App where updatedDate>?", newestConsumptionUpdateTime);
    }*/

    /**
     * 查询需要更新的app信息
     *
     * @return app列表
     */
    private List<App> selectNewApps() {
        if (newestAppUpdateTime == null) {
            return syncDao.find("from App");
        }
        return syncDao.find("from App where updatedDate>?", newestAppUpdateTime);
    }

    private List<Module> selectModules() {
        if (newestModuleUpdateTime == null) {
            return syncDao.find("from Module");
        }
        return syncDao.find("from Module where updatedDate>?", newestModuleUpdateTime);
    }

}
