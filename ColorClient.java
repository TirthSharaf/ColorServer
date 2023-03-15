/*
1. Name: Tirth Sharaf

2. Date: 2022-01-09

3. Java version: 19.0.1.1

4. Precise command-line compilation examples / instructions:
> javac ColorServer.java
>Javac ColorClient.java

5. Precise examples / instructions to run this program:
In separate shell windows: To run on localhost
CMD window 1> java ColorServer
CMD window 2> java ColorClient
CMD window 3> java ColorClient
[...]
CMD window N> java ColorClient

6. Full list of files needed for running the program:
 a. ColorServer.java
 b. ColorClient.java


7. Notes:

We are Using TCP/IP connection to connect our color server and client, we request color request from client and the
server responds by giving us back a randomized color. you can run as many client as  you want and our color server will
 separate all th requests with the help of color worker class

 8. Thanks
 Thanks:

https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html (Code dated 2019-07-09, by Ramesh)
https://rollbar.com/blog/java-socketexception/#
Also: Hughes, Shoffner and Winslow for Inet code.

And to Dr. Clark Elliot for providing the source code for this practical

*/


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ColorClient {
    private static int clientColorLog = 0;//  this is a counter to keep track of number of colors sent and received

    public static void main(String argv[]) {
        ColorClient colcli = new ColorClient(argv);// create an instance of ColorClient
        colcli.run(argv);
    }

    public ColorClient(String argv[]) {

        System.out.println("\nFOR YOUR REFERENCE THIS IS A CONSTRUCTOR USE IT IF NECESSARY\n");
    }

    public void run(String argv[]) {
        // Determining the server name
        String serverName;
        if (argv.length < 1) {
            serverName = "localhost"; // We don't have a server name that's why we have put the default which is local host
        } else {
            serverName = argv[0];
        }
        String colorFromClient = "";
        Scanner consoleIn = new Scanner(System.in);

        System.out.print("PLEASE ENTER YOUR USER ID:");
        System.out.flush();
        String userName = consoleIn.nextLine();
        System.out.println("HELLO " + userName);

        do {
            System.out.print(" PLEASE ENTER A COLOR, OR TYPE 'QUIT' TO END: ");
            colorFromClient = consoleIn.nextLine();

            if (colorFromClient.indexOf("quit") < 0) {
                getColor(userName, colorFromClient, serverName);
                // sending the color to the server
            }
        } while (colorFromClient.indexOf("quit") < 0);

        System.out.println("CANCELLED AS PER YOUR REQUEST:");
        System.out.println(userName + ", YOU REQUESTED AND RECEIVED " + clientColorLog + " colors.");

    }


    void getColor(String userName, String colorFromClient, String serverName){
        try{
            // Creating an instance of ColorData to store the Login, color Request, and color log
            ColorData colorObj = new ColorData();
            colorObj.Login = userName;
            colorObj.colorRequest = colorFromClient;
            colorObj.colorLog = clientColorLog;
            // Connecting to the server
            Socket socket = new Socket(serverName, 45565);
            System.out.println("\n CONNECTION SUCCESSFULLY ESTABLISHED WITH THE COLOR SERVER AT PORT 45,565 ");
           // Sending the serialized data to the server
            OutputStream OutputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(OutputStream);

            oos.writeObject(colorObj);
            System.out.println(" SERIALIZED JAVA OBJECT HAVE BEEN SENT TO COLOR SERVER'S SERVER SOCKET");
            // Receiving the response from the server
            InputStream InpStream= socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(InpStream);
            ColorData InpObject = (ColorData) ois.readObject();

            // Updating the color count
            clientColorLog = InpObject.colorLog;

            // Printing the response from the server
            System.out.println("\n RESPONSE FROM SERVER :");
            System.out.println(InpObject.notes);
            System.out.println("THE COLOR YOU GOT IS: "+ InpObject.colorResponse);
            System.out.println("YOUR COLOR COUNT IS:" + InpObject.colorLog + "\n");

            // Closing the connection to the server
            System.out.println("CLOSING THE CONNECTION TO THE SERVER \n");
            socket.close();
        } catch (ConnectException ConExp) {
            System.out.println("\n Bummer!, COLOR SERVER REFUSED YOUR CONNECTION! TRY AGAIN AFTER SOME TIME. \n");
            ConExp.printStackTrace();
        }catch (UnknownHostException UnHex){
            System.out.println("\n UNKNOWN HOST ERROR! \n");
            UnHex.printStackTrace();
        }catch (ClassNotFoundException ClsEx){
            ClsEx.printStackTrace();
        }catch (IOException IoEx){
            IoEx.printStackTrace();
        }
    }
}



