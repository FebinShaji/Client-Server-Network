//Imports the necessary directories
import java.io.*;
import java.net.*;

//This class handles the Client side of the connection
public class Client {

  public static void main(String[] args) {

    //Initialises the socket, PrintWriter and BufferedReader
    Socket sock = null;
    PrintWriter socketOut = null;
    BufferedReader socketIn = null;

    //This tries to connect to the socket and creates a new BufferedReader and PrintWriter
    try {
      sock = new Socket("localhost", 9300);

      socketOut = new PrintWriter(sock.getOutputStream(), true);
      socketIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
    //This catches an error if the socket doesnt know about the host and then exits
    catch (UnknownHostException e) {
      System.err.println("Error: Don't know about host.\n");
      System.exit(-1);
    }
    //This catches an error if the scoket cannot create a connection with the server and then exits
    catch (IOException e) {
      System.err.println("Error: Couldn't get I/O for the connection to host.\n");
      System.exit(-1);
    }

    //Main sequence of code for the Client side of the communication
    try {
      //Writing the cmd line argument to the server and in turn reading from the server
      socketOut.println(args[0]);
      String numOfLists = socketIn.readLine();
      String numOfMembers = socketIn.readLine();
      //Converting the cmd line arguments received from the server into integers
      int listsNumber = Integer.parseInt(numOfLists);
      int membersNumber = Integer.parseInt(numOfMembers);

      //This checks if the user want to access the totals feature and carries out the approprate response
      if (args[0].equals("totals")) {
        System.out.println("There are " + numOfLists + " list(s), each with a maximum size of " + numOfMembers);
        for (int i = 1; i <= listsNumber; i++) {
          String numOfMembersInList = socketIn.readLine();
          System.out.println("List " + i + " has " + numOfMembersInList + " member(s)");
        }
      }

      //This checks if the user want to access the join feature and carries out the approprate response
      else if (args[0].equals("join")) {
        if (args.length != 3) {
          System.out.println("Error: Incorrect submission for feature");
          System.exit(-1);
        }
        int joinListNumber = Integer.parseInt(args[1]);
        if (joinListNumber > listsNumber || joinListNumber < 1)  {
          System.out.println("Error: That list does not exist");
          System.exit(-1);
        }
        else  {
          socketOut.println(args[1]);
          socketOut.println(args[2]);

          String res = socketIn.readLine();
          System.out.println(res);
        }
      }

      //This checks if the user wants to access the list feature and carries out the approprate response
      else if (args[0].equals("list")) {
        String count = "0";
        if (args.length != 2) {
          System.out.println("Error: Incorrect submission for feature");
          System.exit(-1);
        }
        int listListNumber = Integer.parseInt(args[1]);
        if (listListNumber > listsNumber || listListNumber < 1)  {
          System.out.println("Error: That list does not exist");
          System.exit(-1);
        }
        else  {
          socketOut.println(args[1]);

          for (int i = 1; i <= membersNumber; i++) {
            count = socketIn.readLine();
          }
          int countInt = Integer.parseInt(count);

          if (countInt > 0) {
            for (int i = 1; i <= countInt; i++) {
              String res = socketIn.readLine();
              System.out.println(res);
            }
          }
          else  {
            System.out.println("Error: List has no current members");
          }
        }
      }
      else  {
        System.out.println("Error: Please provide an appropriate input");
        System.exit(-1);
      }
    }
    //Will execute if it can not run the code in the try block
    catch (IOException e) {
      System.err.println("Error: I/O exception during execution\n");
      System.exit(-1);
    }
  }
}
