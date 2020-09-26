import Utils.Base.JsonUtils;
import Utils.LoginUtils;
import Utils.Base.QRCodeUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Scanner;

public class LoginTest{
    public static void main(String[] args) throws IOException, WriterException {
        JSONObject jb = LoginUtils.getQrCodeUrl();
        QRCodeUtils.generateQRCodeImage((String)jb.get("qrUrl"));
        System.out.println("请用BILIBILI手机端扫描桌面二维码");
        new Scanner(System.in).nextLine();
        JSONObject res = LoginUtils.getLoginInfo((String)jb.get("oauthKey"));
        JsonUtils.printJObject(res, new StringBuffer());
    }
}

