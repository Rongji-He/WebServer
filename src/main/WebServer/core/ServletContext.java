package core;

/*
*
* A class to store all servlet obj
* New service and its servlet are added here
*
*
* */
import Servlet.ChangePwdServlet;
import Servlet.HttpServlet;
import Servlet.LoginServlet;
import Servlet.RegServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
    private static Map<String, HttpServlet> servletMap;

    static{
        servletMap= new HashMap<>();
        initServletMap();
    }

    private static void initServletMap() {
        //new service and its servlet are added here
        servletMap.put("/myweb/reg",new RegServlet());
        servletMap.put("/myweb/login",new LoginServlet());
        servletMap.put("/myweb/change",new ChangePwdServlet());
    }

    public static HttpServlet getServlet(String path){
        return servletMap.get(path);
    }
}
