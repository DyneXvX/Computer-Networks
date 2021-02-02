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
        System.out.println("-------Host: 139.62.210.153-------");
        System.out.println("-----Justin the port is 3287------");

        // Once again possible IO Exceptions build catch.
        ServerSocket serverSocket = new ServerSocket(3287);
        Socket socket = serverSocket.accept();

        System.out.println("The connection has been accepted.");       
        BufferedReader reader; //in

        // Get information from the ClientSide and send it back out
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
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
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String choice = reader.readLine();
            switch (choice) {
                
                //Date and Time
                case "1":
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime timeNow = LocalDateTime.now();
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(date);
                    writer.println(formatter.format(timeNow));
                    continue;
                
                // Server Uptime
                case "2":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(serverCMD("uptime"));
                    continue;
                
                //Memory In use
                case "3":
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(serverCMD("netstat"));
                    writer.flush();
                    continue;
                    
                case "End":
                    writer.println("ServerSide shutting down.");
                    input.close();
                    writer.close();
                    break;
                default:
                    writer = new PrintWriter(socket.getOutputStream(), true);    
                    writer.println("Justin we broke something.");
                    writer.flush();
                    

            }
            break;
        }
        serverSocket.close();
    }

	public static String serverCMD(String x) throws IOException {
		String s = "";
		String l; 
		Process p = Runtime.getRuntime().exec(x);
		BufferedReader stdInput = new BufferedReader(new 
                InputStreamReader(p.getInputStream()));

  
           while((l = stdInput.readLine()) != null) {
        	   s ="\n" + l + s;
           }
      return s;
 
	}
}
