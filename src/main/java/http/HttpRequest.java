package http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private String protocol;



    private Socket socket;
    private InputStream is;

    private Map<String, String> headers;

    public HttpRequest(Socket socket) {
        this.socket = socket;
        headers = new HashMap<>();
        try {
            this.is = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server is parsing the request...,");
        parseRequestLine();

        parseHeaders();

        parseContent();

        System.out.println("Request parsing completed.");
    }


    public void parseRequestLine(){

        System.out.println("Parsing request line....");

        try {
            String line = readLine();
            System.out.println(line);
            String [] data = line.split("\\s");
            method = data[0];
            uri = data[1];
            protocol = data[2];


        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Request line parsing completed.");
    }

    public void parseHeaders(){
        System.out.println("Parsing headers....");

        try {
            while(true){
                String line = readLine();
                if(line.equals("")){
                    break;
                }
                String [] data = line.split(":\\s");
                headers.put(data[0],data[1]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Header parsing completed.");
    }

    public void parseContent(){

    }



    private String readLine() throws IOException {

        StringBuilder sb = new StringBuilder();
        int d = -1;
        char c1 = 'a', c2;
        while((d=is.read())!=-1){
            c2 = (char)d;
            if(c2 == 10 && c1 ==13){
                break;
            }
            sb.append(c2);
            c1=c2;
        }

        return sb.toString().trim();
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeader(String key){
        return headers.get(key);
    }



}
