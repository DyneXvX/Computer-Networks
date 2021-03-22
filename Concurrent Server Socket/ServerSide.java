import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.*;
import java.io.*;
import java.lang.Runtime;
import java.lang.Process;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        BufferedReader reader; // in
        
        // Get information from the ClientSide and send it back out
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String choice;

        // Build Client Choices for testing
        while (true) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            choice = reader.readLine();
            if (choice == null) {
                break;
            }

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
        String s = "";
        String line;
        Process process = Runtime.getRuntime().exec(x);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            s ="\n" + line + s;
        }
        return s;

    }

}
