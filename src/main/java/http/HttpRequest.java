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

    private String requestURI;
    private String queryString;
    private Map<String, String> parameters;

    private Socket socket;
    private InputStream is;

    private Map<String, String> headers;

    public HttpRequest(Socket socket) throws EmptyRequestException {
        this.socket = socket;
        headers = new HashMap<>();
        parameters = new HashMap<>();
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


    public void parseRequestLine() throws EmptyRequestException {

        System.out.println("Parsing request line....");

        try {
            String line = readLine();
            //System.out.println(line);
            if("".equals(line)){
                throw new EmptyRequestException();
            }

            String [] data = line.split("\\s");
            method = data[0];
            uri = data[1];
            parseURI();
            protocol = data[2];


        } catch (IOException e) {
            e.printStackTrace();
        } catch(EmptyRequestException e){
            throw e;
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

    /*
    * handling reg service
    * */
    public void parseURI(){
        if(uri.contains("?")){
            String []data = uri.split("\\?");
            requestURI= data[0];
            if(data.length> 1){
                queryString= data[1];
                String []info= queryString.split("&");
                for(String s: info){
                    String [] arr= s.split("=");
                    if(arr.length> 1){
                        parameters.put(arr[0], arr[1]);
                    }else{
                        parameters.put(arr[0], null);
                    }

                }

            }
        }else{
            requestURI= uri;
        }
    }

    public void parseContent(){}

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

    public String getRequestURI() {
        return requestURI;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getParameter(String key){
        return parameters.get(key);
    }

}
