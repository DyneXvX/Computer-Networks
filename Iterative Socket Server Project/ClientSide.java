import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;

public class ClientSide {

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Iterative socket server project");
        System.out.println("Brought to you by Justin Thoms");
        System.out.println("..............................");

        try {

            // System input information:
            // User network address requested:
            System.out.println("Please input the network address you wish to test today.");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String address = input.readLine();

            // User Port address requested:
            System.out.println("Please input the port to use.");
            input = new BufferedReader(new InputStreamReader(System.in));
            int port = Integer.parseInt(input.readLine());
            
            //note these can through IO Exceptions build a catch for this.
            Socket socket = new Socket(InetAddress.getByName(address), port);

            //From class zoom menu:
            //And directly taken option from instructions.
            System.out.println("How many client request do you want to generate?");
            input = new BufferedReader(new InputStreamReader(System.in));
            int request = Integer.parseInt(input.readLine());

            //send information back and forth between Server and Client
            PrintWriter out;
            BufferedReader in;

            //setup the timers
            Instant start;
            Instant end;
            Duration timeRan;
            Duration total = Duration.ZERO;

            //Menu
            System.out.println("-----------------Menu-------------------------------------");
            System.out.println("------------Please enter a number-------------------------");
            System.out.println("1 - Date and Time on the Server");
            System.out.println("2 - For the Server Uptime");
            System.out.println("3 - For Current Memory Use on the Server");
            System.out.println("4 - For Net Stats ('List of Current Connections on the Server ')");
            System.out.println("5 - For a list of Current Users on the Server");
            System.out.println("6 - For a list of Current Running Processes on the Server");
            System.out.println("---------------------------------------------------------");
            

            
            for (int i = 0; i < request; i ++)
            {
                System.out.println("Make a request please");

                //get menu choice from the user
                BufferedReader menuChoice = new BufferedReader(new InputStreamReader(System.in));
                menuChoice = new BufferedReader(new InputStreamReader(System.in));
                int choice = Integer.parseInt(menuChoice.readLine());

                out = new PrintWriter(socket.getOutputStream(), true);
                start = Instant.now();
                out.println(choice);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                end = Instant.now();
                
                timeRan = Duration.between(start, end);
                total = total.plus(timeRan);

                //display time to the client
                System.out.println(in.readLine());
                System.out.println("The time it took was: " + timeRan.toNanos() + " nanoseconds.");
                
                //close connection if total will be what the requested number is.
                if((i + 1) == request)
                {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("End");
                    in.close();
                    out.close();
                }

            }
        } 
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}