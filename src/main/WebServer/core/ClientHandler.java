package core;

import Servlet.ChangePwdServlet;
import Servlet.HttpServlet;
import Servlet.LoginServlet;
import Servlet.RegServlet;
import http.EmptyRequestException;
import http.HttpRequest;
import http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable{

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket =socket;
    }

    /**
     * HTTP server host routines:
     * 1. Parse request from client
     *      1.1 Parse request line
     *      1.2 Parse headers
     *      1.3 Parse content
     *
     * 2. Handle the request
     *      2.1 get uri from step 1
     *      2.2 find corresponding file
     * 3. Send response to client
     *      3.1 Send status line
     *      3.2 Send response headers
     *      3.3 Send content(file)
     */

    public void run() {
        try{

            HttpRequest req = new HttpRequest(socket);
            String path = req.getRequestURI();
            HttpResponse resp = new HttpResponse(socket);
            HttpServlet sl = ServletContext.getServlet(path);
            if(sl != null){
                sl.service(req,resp);
            } else{
                File file = new File("./webapps"+path);

                if(file.exists()){
                    resp.setEntity(file);
                }else{
                    //File notFound = new File("./webapps/root/404.html");
                    resp.setEntity(new File("./webapps/root/404.html"));
                    resp.setStatusCode(404);
                    resp.setStatusReason("Not Found");

                }
            }
            resp.flush();
        }catch (EmptyRequestException e){
            System.out.println("Server received an empty request!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
