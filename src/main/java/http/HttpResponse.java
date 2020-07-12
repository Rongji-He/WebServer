package http;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {


    private int statusCode = 200;
    private String statusReason = "OK";
    private File entity;
    private OutputStream os;
    private Socket socket;

    private Map<String,String> headers;

    public HttpResponse(Socket socket){

        try {
            this.socket= socket;
            this.os = socket.getOutputStream();
            headers = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush(){

        sendStatusLine();
        sendHeaders();
        sendContent();

    }

    public void sendStatusLine(){

        try {
            os.write(("HTTP/1.1 "+statusCode + statusReason).getBytes(StandardCharsets.ISO_8859_1));
            os.write(13);
            os.write(10);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void sendHeaders(){

        try {
            for(Map.Entry<String, String> e :headers.entrySet()){
                String key = e.getKey();
                String value = e.getValue();
                String line = key + ": " + value;
                os.write(line.getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);
            }
            os.write(13);
            os.write(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendContent(){
        try(FileInputStream fis = new FileInputStream(entity);){

            byte [] data = new byte[1024*10];
            int len =-1;

            while((len = fis.read(data))!=-1){
                os.write(data, 0, len);
            }

        }catch (Exception e){
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
        String fname = entity.getName();
        int index = fname.lastIndexOf(".");
        String ext = fname.substring(index+1);
        String mime = HttpContext.getMimeType(ext);

        putHeader("Content-Type", mime);
        putHeader("Content-Length", entity.length()+"");
    }

    public void putHeader(String key, String value){
        headers.put(key, value);
    }

}
