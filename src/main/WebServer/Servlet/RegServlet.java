package Servlet;


/*
*
* A servlet-ish class handling registration service
*
*
* */

import http.HttpRequest;
import http.HttpResponse;

import java.io.File;

public class RegServlet {
    public void service(HttpRequest req, HttpResponse resp){
        String username= req.getParameter("username");
        String password= req.getParameter("password");
        String nickname= req.getParameter("nickname");
       //System.out.println(req.getParameter("age"));
        int age= Integer.parseInt(req.getParameter("age"));

        resp.setEntity(new File("./webapps/myweb/reg_success.html"));
    }
}
