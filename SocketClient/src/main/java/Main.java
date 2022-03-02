public class Main {

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        socketClient.startConnection("127.0.0.1", 5050);
    }
}
