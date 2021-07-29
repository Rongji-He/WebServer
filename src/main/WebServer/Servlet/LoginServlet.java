package Servlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/*
*
* Login service
*
*/
public class LoginServlet extends HttpServlet{
    public void service(HttpRequest req, HttpResponse resp){
        String username= req.getParameter("username");
        String password= req.getParameter("password");

        try(RandomAccessFile raf= new RandomAccessFile("user.dat", "r");){
            for(int i= 0; i<raf.length(); i=i+100){
                raf.seek(i);
                byte [] data = new byte[32];
                raf.read(data);
                String name= new String(data, StandardCharsets.UTF_8).trim();

                if(name.equals(username)){
                    raf.read(data);
                    String pwd= new String(data,StandardCharsets.UTF_8).trim();
                    if(pwd.equals(password)){
                        forward("/myweb/login_success.html",req, resp);
                        return;
                    }
                    break;
                }
            }
            forward("/myweb/login_fail.html",req, resp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
