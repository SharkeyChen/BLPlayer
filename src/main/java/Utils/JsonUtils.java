package Utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

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
