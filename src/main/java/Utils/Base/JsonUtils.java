package Utils.Base;

import com.alibaba.fastjson.JSONObject;

/**
 * Author：Sharkey
 * Date：2020/09/24
 * 处理JSONObject的工具类
 */

public class JsonUtils {

    /**
     * 将字符串转换为JSONObject
     * @param json
     * @return JSONObject
     */
    public static JSONObject JsonToObject(String json){
        JSONObject res = null;
        System.out.println(json);
        try{
            res = JSONObject.parseObject(json);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSONObject格式化输出到控制台
     * @param jb
     * @param prefix
     */
    public  static void printJObject(JSONObject jb, StringBuffer prefix){
        if(jb == null){
            return ;
        }
        System.out.println("{");
        prefix.append("\t");
        for(JSONObject.Entry<String, Object> entry : jb.entrySet()){
            System.out.print(prefix.toString() + entry.getKey() + ": ");
            if(entry.getValue() instanceof JSONObject){
                printJObject((JSONObject)entry.getValue(), prefix);
            }
            else{
                System.out.println(entry.getValue().toString());
            }
        }
        prefix.deleteCharAt(prefix.length() - 1);
        System.out.println(prefix.toString() + "}");
    }
}
