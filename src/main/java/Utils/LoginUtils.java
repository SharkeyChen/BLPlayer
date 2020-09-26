package Utils;

import Utils.Base.HttpClientUtils;
import Utils.Base.JsonUtils;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;


/**
 * 登陆工具
 * Author：Sharkey
 * Date: 2020/09/22
 */
public class LoginUtils {

    // 登陆二维码信息链接
    private static String qrCodeUrl = "http://passport.bilibili.com/qrcode/getLoginUrl";

    // 登陆链接
    private static String qrCodeInfo = "http://passport.bilibili.com/qrcode/getLoginInfo";

    /**
     * 获取登陆二维码信息
     * @return QRCode的密文,以及oauthKey,两者包含在一个字典里
     * oauthKey在180秒后过期
     * @throws IOException
     */
    public static JSONObject getQrCodeUrl(){
        JSONObject jb = HttpClientUtils.httpGetJSONObject(qrCodeUrl, null, true);
        JsonUtils.printJObject(jb, new StringBuffer());
        if(jb == null){
            return null;
        }
        JSONObject resp = new JSONObject();
        String url = (String)jb.getJSONObject("data").get("url");
        String oauthKey = (String)jb.getJSONObject("data").get("oauthKey");
        resp.put("qrUrl", url);
        resp.put("oauthKey", oauthKey);
        return resp;
    }

    /**
     *
     * @param oauthKey
     * @return 返回是否成功登陆，若登陆成功，则返回用户信息（保存在data中的url里）
     * @throws IOException
     */
    public static JSONObject getLoginInfo(String oauthKey){
        JSONObject res = HttpClientUtils.httpPostJSONObject(qrCodeInfo + "?oauthKey=" + oauthKey, null);
        return res;
    }

}
