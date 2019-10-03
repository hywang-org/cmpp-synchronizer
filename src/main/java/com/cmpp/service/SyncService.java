package com.cmpp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmpp.common.JsonBuilder;
import com.cmpp.common.redis.AppInfoRedis;
import com.cmpp.entity.*;
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
    public void syncAppInfoToRedis() {
        this.syncApps();
        this.syncModules();
        this.syncSecret();
        this.syncConsumption();
    }

    private void syncConsumption() {
        List<Consumption> consumptions = syncDao.find("from Consumption");
        if (CollectionUtils.isEmpty(consumptions)) {
            return;
        }
        for (Consumption consumption : consumptions) {
            Map<String, String> map = new HashMap<>(2);
            map.put(TOTAL_NUM, String.valueOf(consumption.getTotalNum()));
            map.put(USED_NUM, String.valueOf(consumption.getUsedNum()));
            appInfoRedis.hset(consumption.getAppId(), map);
        }
    }

    /**
     * 同步channel信息到redis
     */
    public void syncChannelToRedis() {
        List<Channel> channels = this.selectChannels();
        if (CollectionUtils.isEmpty(channels)) {
            return;
        }
        for (Channel channel : channels) {
            Map<String, String> map = new HashMap<>();
            map.put(SP_NAME, channel.getSpName());
            map.put(SP_TYPE, String.valueOf(channel.getSpType()));
            map.put(SP_STATUS, String.valueOf(channel.getSpStatus()));
            map.put(SP_IP, channel.getSpIp());
            map.put(SP_PORT, String.valueOf(channel.getSpPort()));
            map.put(LOGIN_NAME, channel.getSpLoginName());
            map.put(LOGIN_PWD, channel.getSpLoginPwd());
            appInfoRedis.hset(channel.getSpId(), map);
        }
    }

    private List<Channel> selectChannels() {
        return syncDao.find("from Channel");
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
            map.put(USER_ID, app.getUserId());
            map.put(PROTOCOL_TYPE, String.valueOf(app.getProtocolType()));
            map.put(CALLBACK_URL, app.getCallbackUrl());
            map.put(APP_STATUS, String.valueOf(app.getAppStatus()));
            map.put(EXTEND_CODE, app.getExtendCode());
            map.put(SPEED_LIMIT, String.valueOf(app.getSpeedLimit()));
            map.put(SEND_BEGIN_TIME, String.valueOf(app.getSendBeginTime()));
            map.put(SEND_END_TIME, String.valueOf(app.getSendEndTime()));
            map.put(CHANNEL, app.getChannel());
            map.put(MAX_CONNECTION, String.valueOf(app.getMaxConnection()));
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
            appInfoRedis.hset(proSecret.getAppId(), APP_SECRET, proSecret.getAppSecret());
        }
    }

    private List<ProSecret> selectProSecrets() {
        return syncDao.find("from ProSecret");
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
        return syncDao.find("from App");
    }

    private List<Module> selectModules() {
        return syncDao.find("from Module");
    }
}
