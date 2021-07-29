package Servlet;


/*
*
* A servlet-ish class handling registration service
*
*/

import http.HttpRequest;
import http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RegServlet extends HttpServlet{
    public void service(HttpRequest req, HttpResponse resp){
        String username= req.getParameter("username");
        String password= req.getParameter("password");
        String nickname= req.getParameter("nickname");
        int age= Integer.parseInt(req.getParameter("age"));

        try(RandomAccessFile raf= new RandomAccessFile("user.dat", "rw");){
            //each user record is set to 100 byte, i.e. i=i+100
            for(int i= 0; i<raf.length(); i=i+100){
                raf.seek(i);
                byte [] data = new byte[32];
                raf.read(data);
                String name= new String(data, StandardCharsets.UTF_8).trim();
                if(name.equals(username)){
                    forward("/myweb/reg_fail_username_existed.html",req, resp);
                    return;
                }
            }
            raf.seek(raf.length());
            byte[] data =username.getBytes(StandardCharsets.UTF_8);
            data= Arrays.copyOf(data,32);
            raf.write(data);

            data =password.getBytes(StandardCharsets.UTF_8);
            data= Arrays.copyOf(data,32);
            raf.write(data);

            data =nickname.getBytes(StandardCharsets.UTF_8);
            data= Arrays.copyOf(data,32);
            raf.write(data);

            raf.writeInt(age);
            forward("/myweb/reg_success.html",req, resp);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
