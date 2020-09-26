import Entity.MyBean;
import Utils.Base.XMLUtils;

public class MyBeanTest {
    public static void main(String[] args){
        MyBean bean = new MyBean("Info","DedeUserID=107829092&DedeUserID__ckMd5=c8d878848ed7888e&Expires=15551000&SESSDATA=21a42562%2C1616666800%2Cdc30a%2A91&bili_jct=035d5c001ea764125d4ce775029fea75&gourl=http%3A%2F%2Fwww.bilibili.com");
        bean.print();

        //测试XML创建
        XMLUtils.createXMLFile();

        //测试XML写入
        XMLUtils.XMLWriter(bean);

        //测试XML读取
        MyBean nBean = XMLUtils.XMLReader("Info");
        if(nBean != null){
            nBean.print();
        }
    }
}
