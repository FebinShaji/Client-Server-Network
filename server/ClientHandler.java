//Imports the necessary directories
import java.net.*;
import java.io.*;
import java.util.*;

//This is a mini server that each Client can connect to
public class ClientHandler extends Thread {

    //Initialises the variables that are needed in Client Handler
    private Socket socket = null;
    public static int listsNumber = 0;
    public static int membersNumber = 0;
    public static String[][] list;

    //Constructor for the ClientHandler class
    public ClientHandler(Socket socket, int listsNumber, int membersNumber, String[][] list) {
      super("ClientHandler");
		  this.socket = socket;
      this.listsNumber = listsNumber;
      this.membersNumber = membersNumber;
      this.list = list;
    }

    public void run() {

      try {
        //Sets the PrintWriter and BufferedReader to allow for communication
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        FileWriter fw = new FileWriter("log.txt", true);
        BufferedWriter write = new BufferedWriter(fw);

        //Reads in the first cmd line from the client and outputs the cmd line arguments from the server
        String str = in.readLine();
        out.println(listsNumber);
        out.println(membersNumber);

        //Sets up the information needed to be used in logging and then creates an initial conection log
        InetAddress inet = socket.getInetAddress();
      	Date date = new Date();

        //This checks if the user want to access the totals feature and carries out the approprate response
        if(str.equals("totals")) {
          System.out.println("\nDate: " + date.toString() + "| Connection: " + inet.getHostName() +
          "| Request: Accessing Totals");
          write.write("Date: " + date.toString() + "| Connection: " + inet.getHostName() +
          "| Request: Accessing Totals");
          write.newLine();

          for (int i = 0; i < listsNumber; i++)  {
            int numOfMembersInList = 0;
            for (int j = 0; j < membersNumber; j++) {
              if (list[i][j] != null)  {
                numOfMembersInList = numOfMembersInList + 1;
              }
            }
            out.println(numOfMembersInList);
          }
        }

        //This checks if the user want to access the join feature and carries out the approprate response
        if (str.equals("join")) {
          String listJoinNum = in.readLine();
          String name = in.readLine();

          System.out.println("\nDate: " + date.toString() + "| Connection: " + inet.getHostName() +
          "| Request: Accessing Join" );
          write.write("Date: " + date.toString() + "| Connection: " + inet.getHostName() +
          "| Request: Accessing Join");
          write.newLine();

          int listJoinNumInt = Integer.parseInt(listJoinNum);
          for (int i = 0; i < membersNumber; i++)  {
            if (list[listJoinNumInt-1][i] == null)  {
              list[listJoinNumInt-1][i] = name;
              out.println("Success.");
              break;
            }
          }
          out.println("Failed.");
        }

        //This checks if the user wants to access the list feature and carries out the approprate response
        if (str.equals("list")) {
          String listListNum = in.readLine();

          System.out.println("\nDate: " + date.toString() + "| Connection: " + inet.getHostName() +
          "| Request: Accessing List");
          write.write("Date: " + date.toString() + "| Connection: " + inet.getHostName() +
          "| Request: Accessing List");
          write.newLine();

          int listListNumInt = Integer.parseInt(listListNum);
          int numOfMembersInList = 0;
          for (int j = 0; j < membersNumber; j++) {
            if (list[listListNumInt-1][j] != null)  {
              numOfMembersInList = numOfMembersInList + 1;
            }
          out.println(numOfMembersInList);
          }
          for (int i = 0; i < membersNumber; i++)  {
            if (list[listListNumInt-1][i] != null)  {
              out.println(list[listListNumInt-1][i]);
            }
          }
        }

        //Closes the socket and the PrintWriter and BufferedReader and logging files
        write.close();
        fw.close();
        out.close();
        in.close();
        socket.close();
      }
      catch (IOException e) {
        e.printStackTrace();
		  }
    }
}
