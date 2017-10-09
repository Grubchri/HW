import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class MultiThread extends Thread{

    
    public void runServer() throws InterruptedException{
        ServerSocket server;
        try{
            server=new ServerSocket(1111);
            while(true){
                Socket client = server.accept();
                ClientThread ct=new ClientThread(client);
                ct.start();
                client.close();
            }
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    
    
}
