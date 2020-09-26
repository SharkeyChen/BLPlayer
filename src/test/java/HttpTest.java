import Utils.Base.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;

public class HttpTest {

    public static void main(String[] args){
        String url = "http://api.bilibili.com/nav?SESSDATA=de070f5d%2C1608104391%2Cc1c38*61";
        JSONObject jb = new JSONObject();
        System.out.println(HttpClientUtils.httpGetString(url, null));
    }
}
