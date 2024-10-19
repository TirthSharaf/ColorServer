# Color Server-Client Java Program

This project implements a basic server-client architecture in Java using socket programming. The system consists of two main components: ColorServer.java and ColorClient.java, which work together to allow multiple clients to connect and interact with the server simultaneously.

## Project Overview:

• ColorServer.java: This file sets up a server that listens for incoming client connections. Once connected, it facilitates communication between the server and multiple clients. Each client sends a message to the server, which responds accordingly.

• ColorClient.java: This file allows a client to connect to the server, send a username, and interact with the server by sending requests and receiving responses.

## Key Features:

• Multiple clients can connect to the server simultaneously.

• Clients can send and receive messages in real-time using socket-based networking.

• The server processes requests from each client independently.

• Customizable client interaction: Clients can choose usernames, and their interactions are processed by the server in real-time.


## Technical Details:
• Developed using Java, compatible with JDK version as per class requirements.

• Involves multi-threaded programming for handling multiple client connections simultaneously.

• Socket programming is used to establish communication between the server and the clients.

• The project is designed to run in separate terminal windows for the server and each client.


## How to Run the Project:
• Compile both files using javac *.java.

• Start the server in one terminal using: java ColorServer.

• Start one or more clients in separate terminals using: java ColorClient.

• Enter a unique username for each client to interact with the server.


## Custom Modifications:

• I added my own explanatory comments to enhance code readability.

•Modified the client-side interaction to allow for more flexibility in the commands sent to the server.


## This project helped me understand the intricacies of network programming in Java, including how to manage multiple client connections and handle server-side processing efficiently.
