package com.mycompany.tuan9;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String name;
    BufferedReader in;
    BufferedWriter out;

    public ClientHandler(Socket s, String n) throws IOException{
        this.name = n;
        this.socket = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            String input = null;
            while(true) {
                input = in.readLine();
                System.out.println("Server received '" + input + "' from Client " + name);
                if(input.equals("bye"))
                    break;
                for(ClientHandler client : Server.clientList)
                    if(!name.equals(client.name)){
                        client.out.write(input + "\n");
                        client.out.flush();
                        System.out.println("Server sent '" + input + "' from Client " + name + "--> Client " + client.name);
                    }
            }
            System.out.println("Closed socket for client " + socket.toString());
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}