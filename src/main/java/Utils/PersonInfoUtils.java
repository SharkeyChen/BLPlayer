package Utils;

import Entity.MyBean;
import Utils.Base.HttpClientUtils;
import Utils.Base.JsonUtils;
import Utils.Base.XMLUtils;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author: Sharkey
 * Date: 2020/09/26
 * 获取个人信息的工具类
 */

public class PersonInfoUtils {
    /**
     * 获取个人信息的URL
     */
    private static final String INFO_URL = "http://api.bilibili.com/nav";


    /**
     * 获取个人信息
     * @return JSONObject
     */
    public static JSONObject getPersonInfo(){
        MyBean bean = XMLUtils.XMLReader("Info");
        if(bean == null){
            System.out.println("还未登陆呢");
            return null;
        }
        String ses = bean.toURLLenCode();
        JSONObject param = new JSONObject();
        param.put("Cookie", ses);
        JSONObject res = HttpClientUtils.httpGetJSONObject(INFO_URL, param);
        return res;
    }
}
