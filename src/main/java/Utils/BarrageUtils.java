package Utils;

/**
 * Author：Sharkey
 * Date：2020/09/23
 * 弹幕工具类
 */

public class BarrageUtils {
    /**
     * 获取弹幕的url，格式为http://comment.bilibili.com/{cid}.xml
     */
    private static String barrageUrl = "http://comment.bilibili.com/";

    /**
     * 获取实时弹幕
     * @param cid
     */
    public static void fetchBarrage(int cid){
        String jb = HttpClientUtils.httpGetString(barrageUrl + cid + ".xml", null);
        System.out.println(jb);
    }
}
