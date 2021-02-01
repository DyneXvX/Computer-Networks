import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        System.out.println();
        System.out.println("---Starting up the Server Side----");
        System.out.println("Waiting for the Client to connect.");
        System.out.println("----------------------------------");
        System.out.println("Host: 139.62.210.153");
        System.out.println("Justin the port is 3287");

        // Once again possible IO Exceptions build catch.
        ServerSocket serverSocket = new ServerSocket(3287);
        Socket socket = serverSocket.accept();

        System.out.println("The connection has been accepted.");       
        BufferedReader in;

        // Get information from the ClientSide and send it back out
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        InputStream input = socket.getInputStream();
        
        //---Requested Build Methods for Class---
        // Date and Time - the date and time on the server
        // Uptime - how long the server has been running since last boot-up
        // Memory Use - the current memory usage on the server
        // Net Stat - lists network connections on the server
        // Current Users - list of users currently connected to the server
        // Running Processes - list of programs currently running on the server

        // Build Client Choices for testing
        while (true) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String choice = in.readLine();
            switch (choice) {
                case "1":
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime timeNow = LocalDateTime.now();
                    out.println(date);
                    out.println(formatter.format(timeNow));
                    continue;

                case "End":
                    out.println("ServerSide shutting down.");
                    input.close();
                    out.close();
                    break;
                default:
                    out = new PrintWriter(socket.getOutputStream(), true);    
                    out.println("Justin we broke something.");
                    out.flush();
                    

            }
            break;
        }
        serverSocket.close();
    }
}
