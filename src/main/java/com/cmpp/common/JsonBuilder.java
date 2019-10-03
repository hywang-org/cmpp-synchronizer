package com.cmpp.common;

import com.alibaba.fastjson.JSONObject;

/**
 * 作者： chengli
 * 日期： 2019/10/2 17:34
 */
public class JsonBuilder {

    private JSONObject jsonObject;

    private JsonBuilder() {
        jsonObject = new JSONObject();
    }

    public static JsonBuilder create() {
        return new JsonBuilder();
    }

    public JsonBuilder json(String key, Object value) {
        jsonObject.put(key, value);
        return this;
    }

    public JSONObject build() {
        return jsonObject;
    }
}
