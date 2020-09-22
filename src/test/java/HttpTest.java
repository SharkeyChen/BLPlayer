import Utils.HttpClientUtils;
import Utils.JsonUtils;
import Utils.LoginUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Scanner;

public class HttpTest {
    private static String searchUrl = "http://api.bilibili.com/x/web-interface/search/all/v2";

    private static String vInfoUrl = "http://api.bilibili.com/x/web-interface/view";

    private static String fetchVideoUrl = "http://api.bilibili.com/x/player/playurl";

    public static void main(String[] args) throws IOException, WriterException {
//        System.out.println("请输入BV号:");
//        Scanner scanner = new Scanner(System.in);
//        String keyword = scanner.next();
//        String res = HttpClientUtils.sendGet(vInfoUrl + "?bvid=BV" + keyword);
//        JSONObject jb = JsonUtils.JsonToObject(res);
//        int cid = (Integer) jb.getJSONObject("data").get("cid");
//        res = HttpClientUtils.sendGet(fetchVideoUrl + "?bvid=" + keyword + "&cid=" + cid);
//        jb = JSONObject.parseObject(res);
//        String url = (String)jb.getJSONObject("data").getJSONArray("durl").getJSONObject(0).get("url");
//        System.out.println(url);
//        HttpClientUtils.DownLoadVideos(url);
    }
}
