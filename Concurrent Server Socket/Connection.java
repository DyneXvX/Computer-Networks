import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.lang.Process;
import java.lang.Runtime;

public class Connection {    
    public static void main(String[] args) throws IOException{
        try(ServerSocket serverSocket = new ServerSocket(3287)){
            System.out.println();
            System.out.println("---Starting up the Server Side----");
            System.out.println("Waiting for the Client to connect.");
            System.out.println("----------------------------------");
            System.out.println("-------Host: 139.62.210.153-------");
            System.out.println("-----Justin the port is 3287------");

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("The connection has been accepted.");
                new Connection(socket).start();
            }
        }catch(IOException ex){
            System.out.println("Server Error: " + ex.getMessage());
        }
    }
}
