package Entity;

import Utils.Base.JsonUtils;
import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;


/**
 * Author：Sharkey
 * Date：2020/09/26
 * 存JSON的实体类，主要用作XML写入
 */
public class MyBean {
    private JSONObject jb;

    private String name;

    public MyBean(String name){
        this.name = name;
    }

    public MyBean(String name, String str){
        this.name = name;
        //反转义
        try{
            str = URLDecoder.decode(str, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] res = str.split("[=&]");
        this.jb = new JSONObject();
        for(int i = 0;i < res.length;i += 2){
            jb.put(res[i], res[i+1]);
        }
    }

    public void setData(String str){
        String[] res = str.split("[=&]");
        this.jb = new JSONObject();
        for(int i = 0;i < res.length;i += 2){
            jb.put(res[i], res[i+1]);
        }
    }

    public JSONObject getJb(){
        return jb;
    }

    public String getName(){
        return name;
    }

    public void print(){
        System.out.println("Bean Name:" + this.name);
        JsonUtils.printJObject(jb, new StringBuffer());
    }

    public String toURLLenCode(){
        StringBuffer res = new StringBuffer();
        for(JSONObject.Entry<String, Object> entry : jb.entrySet()){
            res.append(entry.getKey()).append("=").append(entry.getValue().toString()).append(";");
        }
        return res.toString();
    }
}
