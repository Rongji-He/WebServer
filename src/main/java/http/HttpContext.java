package http;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpContext {
    private static Map<String, String> mimeMapping = new HashMap<>();

    static {
        initMimeMapping();

    }

    private static void initMimeMapping(){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File("conf/web.xml"));
            Element root = doc.getRootElement();

            List<Element> list = root.elements("mime-mapping");
            for(Element el:list){
                String key = el.elementText("extension");
                String value = el.elementText("mime-type");
                mimeMapping.put(key,value);

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static String getMimeType(String ext){
        return mimeMapping.get(ext);
    }
}
