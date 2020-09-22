package Utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    public static JSONObject JsonToObject(String json){
        return JSONObject.parseObject(json);
    }

    public  static void printJObject(JSONObject jb, StringBuffer prefix){
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
