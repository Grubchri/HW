
import java.net.*;
import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientThread extends Thread{
    public Socket clientSocket = null;
    Statement stm;
    
    public ClientThread (Socket s){
        clientSocket=s;
    }
    
    public void run(){
        final String host="localhost";
        final int port=1111;
    
        while(true){
            Socket sock;
            try {
                sock = new Socket(host,port);

                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

                System.out.println("server says:" + br.readLine());

                String n="";
                n="Name";

                int num=0;
                num=(int)(Math.random()*29)+1;

                String wt;

                if(num>0 && num<15){
                    wt="cloudy";
                }else{
                    if(num>15){
                        wt="sunny";
                    }else{
                        wt="cold";
                    }
                }

                n="Name="+n;
                out.println(n);

                wt="Weathertype="+wt;
                out.println(wt);
                String temp="Temperature="+Integer.toString(num);
                out.println(temp);
                out.println("Exit");
                System.out.println(wt+" "+n+" "+temp);

                System.out.println("server says:" + br.readLine());
                Thread.sleep(1000);
                sock.close();

            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
}
