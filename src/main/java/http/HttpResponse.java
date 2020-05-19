package http;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpResponse {


    private int statusCode = 200;
    private String statusReason = "OK";
    private File entity;
    private OutputStream os;
    private Socket socket;

    public HttpResponse(Socket socket){

        try {
            this.socket= socket;
            this.os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush(){
        String line = "Http/1.1 " + statusCode + " " + statusReason;
        try {
            os.write("HTTP/1.1 200 OK".getBytes(StandardCharsets.ISO_8859_1));
            os.write(13);
            os.write(10);

            os.write("Content-Type: text/html".getBytes(StandardCharsets.ISO_8859_1));
            os.write(13);
            os.write(10);

            os.write(("Content-Length: "+entity.length()).getBytes(StandardCharsets.ISO_8859_1));
            os.write(13);
            os.write(10);

            //empty line
            os.write(13);
            os.write(10);

            FileInputStream fis = new FileInputStream(entity);
            byte [] data = new byte[1024*10];
            int len =-1;

            while((len = fis.read(data))!=-1){
                os.write(data, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
    }

}
