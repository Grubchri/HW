
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class HUE_4_01_Client extends Thread {

    
    public static void main(String[] args) throws IOException, InterruptedException{
        
        final String host="localhost";
        final int port=1111;
        
        Scanner sc=new Scanner(System.in);
        
        
        while(true){
            Socket sock=new Socket(host,port);
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
            Thread.sleep(1000);
            wt="Weathertype="+wt;
            Thread.sleep(1000);
            out.println(wt);
            Thread.sleep(1000);
            String temp="Temperature="+Integer.toString(num);
            out.println(temp);
            Thread.sleep(1000);
            out.println("Exit");
            System.out.println(wt+" "+n+" "+temp);

            System.out.println("server says:" + br.readLine());
            
            sock.close();
            
        }
    }
    
}
