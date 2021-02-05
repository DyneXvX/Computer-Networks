import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.*;
import java.io.*;
import java.lang.Runtime;
import java.lang.Process;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        System.out.println();
        System.out.println("---Starting up the Server Side----");
        System.out.println("Waiting for the Client to connect.");
        System.out.println("----------------------------------");
        System.out.println("-------Host: 139.62.210.153-------");
        System.out.println("-----Justin the port is 3287------");

        // Once again possible IO Exceptions build catch.
        ServerSocket serverSocket = new ServerSocket(3287);
        Socket socket = serverSocket.accept();

        System.out.println("The connection has been accepted.");
        BufferedReader reader; // in

        // Get information from the ClientSide and send it back out
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String choice;

        // Build Client Choices for testing
        while (true) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            choice = reader.readLine();

            switch (choice) {

                // Date and Time
                case "1":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime date = LocalDateTime.now();
                    System.out.println(formatter.format(date));
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(formatter.format(date));
                    writer.flush();
                    continue;

                // Server Uptime
                case "2":
                    // System.out.println("uptime");
                    writer.println(serverCMD("uptime"));
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.flush();
                    continue;

                // Memory In use
                case "3":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(serverCMD("cat /proc/meminfo"));
                    writer.flush();
                    continue;

                // Net Stats
                case "4":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(serverCMD("netstat"));
                    writer.flush();
                    continue;

                // Current Users
                case "5":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(serverCMD("who"));
                    writer.flush();
                    continue;

                // Running Process
                case "6":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(serverCMD("ps -aux"));
                    writer.flush();
                    continue;

                // Shut Down
                case "7":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    System.out.println("ServerSide Shutting Down.");
                    reader.close();
                    writer.close();
                    break;

                default:
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println("Justin we broke something.");
                    writer.flush();
            }// end case
            break;

        }
    }

    public static String serverCMD(String x) throws IOException {
        String s = null;
        String line = null;
        Process process = Runtime.getRuntime().exec(x);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            s = line;
        }
        return s;

    }

}
