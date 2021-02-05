import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.ToIntFunction;

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

            // note these can through IO Exceptions build a catch for this.
            Socket socket = new Socket(InetAddress.getByName(address), port);

            // send information back and forth between Server and Client
            PrintWriter writer; // out
            BufferedReader reader; // in

            // setup the timers
            Instant start;
            Instant end;
            Duration timeRan = Duration.ZERO;
            Duration total = Duration.ZERO;
            double average = 0;

            //

            // Menu
            System.out.println("-----------------Menu-------------------------------------------");
            System.out.println("------------Please enter a number-------------------------------");
            System.out.println("1 - Date and Time on the Server---------------------------------");
            System.out.println("2 - For the Server Uptime---------------------------------------");
            System.out.println("3 - For Current Memory Use on the Server------------------------");
            System.out.println("4 - For Net Stats ('List of Current Connections on the Server ')");
            System.out.println("5 - For a list of Current Users on the Server-------------------");
            System.out.println("6 - For a list of Current Running Processes on the Server-------");
            System.out.println("7 - End the test------------------------------------------------");
            System.out.println("----------------------------------------------------------------");
            int choice = 0;
            while (true) {
                // From class zoom menu:
                // And directly taken option from instructions.
                System.out.println("How many client request do you want to generate?");
                input = new BufferedReader(new InputStreamReader(System.in));
                int request = Integer.parseInt(input.readLine());

                System.out.println("Choose a menu choice please");

                // get menu choice from the user
                BufferedReader menuChoice = new BufferedReader(new InputStreamReader(System.in));
                menuChoice = new BufferedReader(new InputStreamReader(System.in));
                choice = Integer.parseInt(menuChoice.readLine());
                for (int i = 0; i < request; i++) {
                    // to server
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    start = Instant.now();
                    writer.println(choice);

                    // from server
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println(reader.readLine());
                    end = Instant.now();

                    // time setup
                    timeRan = Duration.between(start, end);
                    total = total.plus(timeRan);
                }
                if (choice == 7) {
                    System.out.println("Client Side Shutting Down.");
                    System.out.println("Thank you for using a Justin Thoms Java Program");
                    break;
                }
                // display time to the client
                System.out.println("The time it took was: " + timeRan.toNanos() + " Nanoseconds.");
                long millis = timeRan.toMillis();
                System.out.println("You made a total of " + request + " request");
                average = ((double) millis / request) * 100;
                System.out.println("Therefore the average request took a total of " + average + " milliseconds.");

            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}