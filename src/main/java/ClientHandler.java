import http.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
        try {

            HttpRequest req = new HttpRequest(socket);
            File file = new File("./webapps"+req.getUri());
            if(file.exists()){
                //System.out.println("got it!");
                OutputStream os = socket.getOutputStream();

                os.write("HTTP/1.1 200 OK".getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);

                os.write("Content-Type: text/html".getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);

                os.write(("Content-Length: "+file.length()).getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);

                //empty line
                os.write(13);
                os.write(10);

                FileInputStream fis = new FileInputStream(file);
                byte [] data = new byte[1024*10];
                int len =-1;

                while((len = fis.read(data))!=-1){
                    os.write(data, 0, len);
                }
            }else{
               File notFound = new File("./webapps/root/404.html");
               OutputStream os = socket.getOutputStream();
                os.write("HTTP/1.1 404 Not Found".getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);

                os.write("Content-Type: text/html".getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);

                os.write(("Content-Length: "+notFound.length()).getBytes(StandardCharsets.ISO_8859_1));
                os.write(13);
                os.write(10);

                os.write(13);
                os.write(10);

                FileInputStream fis = new FileInputStream(notFound);
                byte [] data = new byte[1024*10];
                int len =-1;

                while((len = fis.read(data))!=-1){
                    os.write(data, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
