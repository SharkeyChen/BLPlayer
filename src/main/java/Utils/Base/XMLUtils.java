package Utils.Base;

import Entity.MyBean;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


/**
 * Author:Sharkey
 * Date:2020/09/26
 * 读写XML文件的工具类，这里主要是存入用户数据
 */
public class XMLUtils {
    private static final String XML_PATH = "C:/Users/DELL/desktop/";

    /**
     * 在指定路径创建一个XML文件
     */
    public static void createXMLFile(){
        try{
            File file = new File(XML_PATH + "Info.xml");
            if(!file.exists()){
                file.createNewFile();
            }
            Document doc = DocumentHelper.createDocument();
            doc.addElement("Resources");
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            FileOutputStream fos = new FileOutputStream(XML_PATH + "Info.xml");
            writer.setOutputStream(fos);
            writer.write(doc);
            System.out.println("创建完毕");
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 向XML文件中写入用户数据信息
     * @param bean
     */
    public static void XMLWriter(MyBean bean){
        SAXReader reader = new SAXReader();
        try{
            Document doc = reader.read(new File(XML_PATH + "Info.xml"));
            Element root = doc.getRootElement();
            Element info = root.addElement(bean.getName());
            JSONObject jb = bean.getJb();
            for(JSONObject.Entry<String, Object> entry : jb.entrySet()){
                info.addElement(entry.getKey()).addText(entry.getValue().toString());
            }
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            FileOutputStream fos = new FileOutputStream(XML_PATH + "Info.xml");
            writer.setOutputStream(fos);
            writer.write(doc);
            System.out.println("写入完毕");
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从XML文件中读取指定节点信息,并封装为一个MyBean
     * @param node
     */
    public static MyBean XMLReader(String node){
        try{
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(XML_PATH + "Info.xml"));
            Element root = doc.getRootElement();
            Element ele = root.element(node);
            if(ele == null){
                System.out.println("节点不存在");
                return null;
            }
            List<Element> EList = ele.elements();
            StringBuffer buffer = new StringBuffer();
            for(Element e : EList){
                buffer.append(e.getName() + "=" + e.getText() + "&");
            }
            if(buffer.length() == 0){
                System.out.println("该节点中为空");
                return null;
            }
            return new MyBean(ele.getName(), buffer.toString());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
