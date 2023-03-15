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

import java.io.*;// Input/output classes
import java.net.ServerSocket;//server socket class
import java.net.Socket;//Java networking classes

// Implement the Serializable interface to allow the object to be serialized
class ColorData implements Serializable{
    String Login;//Login ID of client
    String colorRequest;//the color client is requesting
    String colorResponse;// the color server will be sending back to client
    String notes;// notes/messages from server to client
    int colorLog;// counts the number of colors sent between the server and client('s)
}

// The workerForColor class extends the Thread class and is used to handle client connections in a separate thread.
class ColorWorker extends Thread{
    Socket socket;// Declared a Socket variable to store the client socket
    ColorWorker (Socket soc) {socket = soc;} //Constructor to initialize the client socket

    public void run (){
        try{
            // Created an ObjectInputStream to read the serialized data sent by the client
            InputStream InStream = socket.getInputStream();
            ObjectInputStream ObjectIS = new ObjectInputStream(InStream);

            // Reading the serialized data sent by the client
            ColorData InObject = (ColorData) ObjectIS.readObject();

            // Creating an ObjectOutputStream to write the response to the client
            OutputStream outpStream = socket.getOutputStream();
            ObjectOutputStream objectOS = new ObjectOutputStream(outpStream);

            // Printing the data received from the client
            System.out.println("\n REQUEST FROM CLIENT\n");
            System.out.println("USER ID " + InObject.Login);
            System.out.println("COLOR REQUEST FROM CLIENT; " + InObject.colorRequest);
            System.out.println("CONNECTION/S LOG (STATE!): " + (InObject.colorLog + 1));

            // Generating a random color to send back to the client
            InObject.colorResponse = RandomColor();
            InObject.colorLog++;
            InObject.notes =// Create a message to send back to the client
                    String.format("THANK YOU %s FOR REQUESTING THE COLOR %s", InObject.Login, InObject.colorRequest);

            objectOS.writeObject(InObject);//sends the modified ColorData back to the client.
            System.out.println("ENDING THE CLIENT SOCKET CONNECTION......");
            socket.close();// Close the client socket connection


        } catch (ClassNotFoundException ClsEx){
            ClsEx.printStackTrace();
        } catch (IOException IoEx){
            System.out.println("SERVER ERROR!!!!.");
            IoEx.printStackTrace();
        }
    }

    String RandomColor(){
        String[] colorRange = new String[]
                {
                        "Red", "Blue", "Green", "Yellow", "Magenta", "Silver", "Aqua", "Gray", "Peach", "Orange",
                        "Violet", "Indigo", "Cyan", "Pink", "Mauve", "Lime", "Turquoise", "Teal", "Maroon", "Olive"
                };
        int randomArrayIndex = (int) (Math.random() * colorRange.length);
        return (colorRange[randomArrayIndex]);//will return a random color from the list
    }
}




public class ColorServer {
    public static void main(String[] args) throws Exception
    {
        int queueLength=6; // The length of the queue for incoming connections
        int serverPortNum = 45565; // The port the server will listen on
        Socket socket; // A socket for connecting with a client

        System.out.println("TIRTH'S Server 999.0 IS STARTING UP, LISTENING AT PORT " + serverPortNum + ".\n");

        // Created a new ServerSocket on the specified port, with the specified queue length
        ServerSocket servSock = new ServerSocket(serverPortNum, queueLength);
        System.out.println("PLEASE WAIT!, SERVER SOCKET IS LOOKING FOR CONNECTIONS...");// Printing a message indicating that the server is awaiting connections

        while (true) {// Run indefinitely
            socket= servSock.accept();// Accepts a new connection and assign it to the socket
            System.out.println("CONNECTION SUCCESSFULLY ESTABLISHED FROM " + socket);// Print a message indicating that a new connection has been received
            new ColorWorker(socket).start();// this thread will handle the connection and start it
        }
    }
}







