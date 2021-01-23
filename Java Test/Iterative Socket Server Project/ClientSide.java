import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {

    public static void main(String[] args) {
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
            int port = Integer.parseInt(input.readLine());
            
            //note these can through IO Exceptions build a catch for this.
            Socket socket = new Socket(InetAddress.getByName(address), port);

            //From class zoom menu:
            //And directly taken option from instructions.
            System.out.println("How many client request do you want to generate?");
            int request = Integer.parseInt(input.readLine());

        } 
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}