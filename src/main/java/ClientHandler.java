import http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket =socket;
    }

    /**
     * HTTP server host routines:
     * 1. Parse request from client
     * 2. Handle the request
     * 3. Send response to client
     */

    public void run() {
        try {

            HttpRequest req = new HttpRequest(socket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
