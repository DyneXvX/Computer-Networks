import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        System.out.println("---Starting up the Server Side----");
        System.out.println("Waiting for the Client to connect.");
        System.out.println("----------------------------------");

        // Once again possible IO Exceptions build catch.
        ServerSocket serverSocket = new ServerSocket(6868);
        Socket socket = serverSocket.accept();
        System.out.println("The connection has been accepted.");

        // Get information from the the ClientSide
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // Date and Time - the date and time on the server
        // Uptime - how long the server has been running since last boot-up
        // Memory Use - the current memory usage on the server
        // Netstat - lists network connections on the server
        // Current Users - list of users currently connected to the server
        // Running Processes - list of programs currently running on the server

        // Build output to the ClientSide
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        // Build Client Choices for testing
        while (true) {
            String choice = reader.readLine();
            switch (choice) {
                case "1":
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime timeNow = LocalDateTime.now();
                    writer.println(date);
                    writer.println(formatter.format(timeNow));
                    continue;

                case "Exit":
                    writer.println("ServerSide shutting down.");
                    input.close();
                    writer.close();
                    break;
                default:
                    writer.println("Justin we broke something.");

            }
            break;
        }
        serverSocket.close();
    }
}
