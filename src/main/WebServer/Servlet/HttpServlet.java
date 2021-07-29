package Servlet;


import http.HttpRequest;
import http.HttpResponse;

import java.io.File;

public abstract class HttpServlet {

    public abstract void service(HttpRequest req, HttpResponse resp);

    public void forward(String path, HttpRequest req, HttpResponse resp){
        File file = new File("./webapps"+path);
        resp.setEntity(file);
    }
}
