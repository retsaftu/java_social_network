import websocket.SocketServer;

public class Main {

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.start(5050);
    }
}
