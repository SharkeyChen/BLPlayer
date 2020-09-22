package Utils;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectUtils {
    public static JSONObject ok(){
        return (JSONObject) new JSONObject().put("status", true);
    }

    public static JSONObject error(){
        return (JSONObject) new JSONObject().put("status", false);
    }
}
