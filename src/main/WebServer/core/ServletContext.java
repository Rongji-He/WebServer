package core;

/*
*
* A class to store all servlet obj
*
*
* */
import Servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletContext {
    private static Map<String, HttpServlet> servletMap;

    static{
        servletMap= new HashMap<>();
        initServletMap();
    }

    private static void initServletMap() {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File("./conf/servlet.xml"));
            Element root= doc.getRootElement();
            List<Element> list = root.elements("servlet");
            for(Element e:list){
                String path= e.attributeValue("path");
                String className= e.attributeValue("className");
                Class<?> cls= Class.forName(className);
                HttpServlet servlet= (HttpServlet)cls.newInstance();
                servletMap.put(path, servlet);
            }
        } catch (DocumentException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        //new service and its servlet are added here
        /*servletMap.put("/myweb/reg",new RegServlet());
        servletMap.put("/myweb/login",new LoginServlet());
        servletMap.put("/myweb/change",new ChangePwdServlet());*/
    }

    public static HttpServlet getServlet(String path){
        return servletMap.get(path);
    }
}
