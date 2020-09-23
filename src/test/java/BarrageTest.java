import Utils.BarrageUtils;
import Utils.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;

import java.util.Scanner;

public class BarrageTest {
    private static String videoInfoUrl = "http://api.bilibili.com/x/web-interface/view";

    public static void main(String[] args){
        System.out.println("请输入BV号:");
        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.next();
        JSONObject jb = HttpClientUtils.httpGetJSONObject(videoInfoUrl + "?bvid=BV" + keyword, null);
        int cid = (Integer) jb.getJSONObject("data").get("cid");
        BarrageUtils.fetchBarrage(cid);
    }
}
