//Imports the necessary directories
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

//This handles the Server side of the communication
public class Server {

  //Initialises soem variables that are needed for the Server and ClientHandler
  public static String numOfLists = "0";
  public static String numOfMembers = "0";
  public static int listsNumber = 0;
  public static int membersNumber = 0;
  public static String[][] list = new String[listsNumber][membersNumber];

  public static void main(String[] args) throws IOException {

    //Initialises the socket and the executor service
    ServerSocket server = null;
    ExecutorService service = null;

    //Tries to create the connection that the clients can connect to
    try {
      server = new ServerSocket(9300);
    }
    //Catches an exception if the socket number cant be connected to
    catch (IOException e) {
      System.err.println("Error: Could not listen on port: 2323.");
      System.exit(-1);
    }

    //Sets the number of clients that can connect to the socket to a max number
    service = Executors.newFixedThreadPool(25);

    //Assigns variables that are needed from the cmd line
    numOfLists = args[0];
    numOfMembers = args[1];
    //Converts the cmd line arguments into integers
    listsNumber = Integer.parseInt(numOfLists);
    membersNumber = Integer.parseInt(numOfMembers);
    //Initialses a 2d-array of the appropriate size depending on the cmd line arguments
    String[][] list = new String[listsNumber][membersNumber];

    FileWriter fw = new FileWriter("log.txt", false);

    //Submits the socket connection to the ClientHandler file
    while( true )
    {
      Socket client = server.accept();
      service.submit( new ClientHandler(client, listsNumber, membersNumber, list) );
    }
  }
}
