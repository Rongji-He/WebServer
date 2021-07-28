package Servlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ChangePwdServlet {

    public void service(HttpRequest req, HttpResponse resp) {
        String username = req.getParameter("username");
        String oldPwd = req.getParameter("oldPwd");
        String newPwd = req.getParameter("newPwd");

        try (RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");) {
            //each user record is set to 100 byte, i.e. i=i+100
            for (int i = 0; i < raf.length(); i = i + 100){
                raf.seek(i);
                byte [] data = new byte[32];
                raf.read(data);
                String name= new String(data, StandardCharsets.UTF_8).trim();
                if(name.equals(username)){
                    raf.read(data);
                    String pwd= new String(data,StandardCharsets.UTF_8).trim();

                    if(pwd.equals(oldPwd)){
                        raf.seek(i+32);
                        data= newPwd.getBytes(StandardCharsets.UTF_8);
                        data= Arrays.copyOf(data,32);
                        raf.write(data);
                        resp.setEntity(new File("./webapps/myweb/change_success.html"));
                        return;
                    }else{
                        break;
                    }
                }
            }

            resp.setEntity(new File("./webapps/myweb/change_fail.html"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}