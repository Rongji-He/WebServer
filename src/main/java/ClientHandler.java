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
     * 1. Interpret request from client
     * 2. Handle the request
     * 3. Send response to client
     */

    public void run() {
        try {
            InputStream is = socket.getInputStream();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
