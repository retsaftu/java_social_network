package websocket;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
    private ServerSocket serverSocket;
    private static int ID = 1;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            new ClientHandler(serverSocket.accept(), ID++).start();
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
