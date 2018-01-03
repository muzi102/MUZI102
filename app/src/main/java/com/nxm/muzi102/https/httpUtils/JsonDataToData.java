package com.nxm.muzi102.https.httpUtils;

import com.google.gson.reflect.TypeToken;
import com.nxm.muzi102.base.BuzProfession;

import java.util.ArrayList;
import java.util.List;

/**
 * **************************************************************************************************************
 * 修改日期                         修改人             任务名称                             功能或Bug描述
 * 2018年1月1日23:58:33             lzx              JsonDataToData json数据解析
 * <p>
 * **************************************************************************************************************
 */


public class JsonDataToData {
    private static final String Response = "{\"code\":\"200\",\"data\":[{\"name\":\"外科\",\"id\":55,\"dictId\":3,\"value\":\"1\"},{\"name\":\"内科\",\"id\":56,\"dictId\":3,\"value\":\"2\"},{\"name\":\"儿科\"," +
            "\"id\":57,\"dictId\":3,\"value\":\"3\"},{\"name\":\"妇产科\",\"id\":58,\"dictId\":3,\"value\":\"4\"},{\"name\":\"精神科\",\"id\":59,\"dictId\":3,\"value\":\"5\"},{\"name\":\"口腔科\",\"id\":60,\"dictId\":3,\"value\":\"6\"},{\"name\":\"耳鼻喉科\",\"id\":61,\"dictId\":3,\"value\":\"7\"}]}";


    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static JsonDataToData instance = null;

    /* 私有构造方法，防止被实例化 */
    private JsonDataToData() {
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static JsonDataToData getInstance() {
        if (instance == null) {
            instance = new JsonDataToData();
        }
        return instance;
    }

    public ArrayList<BuzProfession> getBuzProfessions(String response) {
        String codeResponse = JsonUtils.getFieldValue(Response, "data");
        return (ArrayList<BuzProfession>) JsonUtils.parseJsonToList(codeResponse, new TypeToken<ArrayList<BuzProfession>>() {
        }.getType());
    }


}
