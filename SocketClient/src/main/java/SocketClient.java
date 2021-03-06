import com.google.gson.Gson;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
//            ServerNotifications serverNotifications = new ServerNotifications();
//            serverNotifications.start();
            while (true) {
                String message = getMessage();
                if (message.equals("Exit the server...")) {
                    break;
                } else if (isJSONValid(message)) {
                    System.out.println("Server -> This client: ");
                    System.out.println(new JSONArray(message).toString(4));
                    System.out.println("New posts were successfully retrieved! Press Enter to continue...\n");
                } else {
                    System.out.println("Server -> This client: " + message);
                }

                String input = scanner.nextLine();
                sendMessage(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopConnection();
        }
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            new Gson().fromJson(jsonInString, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    public void sendMessage(String message) {
        try {
            if (message.length() > 1) {
                writer.println(message);
                System.out.println("This client -> Server: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        String message = "Exit the server...";
        try {
            message = reader.readLine();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public void stopConnection() {
        try {
            reader.close();
            writer.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void getNotifications() {
//        try {
//            String message = reader.readLine();
//            if (message != null && isJSONValid(message)) {
//                System.out.println("Server -> This client: ");
//                System.out.println(new JSONArray(message).toString(4));
//                System.out.println("New posts were successfully retrieved! Press Enter to continue...\n");
//            } else {
//                System.out.println("Server -> This client: " + message);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private class ServerNotifications extends Thread {
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    int secondsInterval = 1;
//                    getNotifications();
//                    Thread.sleep(1000 * secondsInterval);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
