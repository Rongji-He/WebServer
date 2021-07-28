package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private ServerSocket server;

    public WebServer(){
        try {
            System.out.println("Initializing server...");
            server = new ServerSocket(8008);
            System.out.println("Server initialization complete...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void start(){
        System.out.println();
        try {

            while(true){
                System.out.println("Server is listening...");
                Socket socket = server.accept();
                System.out.println("One client has connected!");
                ClientHandler ch = new ClientHandler(socket);
                Thread thread = new Thread(ch);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
    }


}
