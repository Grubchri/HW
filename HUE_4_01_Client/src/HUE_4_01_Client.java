
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class HUE_4_01_Client {

    
    public static void main(String[] args) throws IOException {
        final String host="localhost";
        final int port=1111;
    
        while(true){
            Socket sock=new Socket(host,port);
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            System.out.println("server says:" + br.readLine());

            BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
            String userInput = userInputBR.readLine();

            out.println(userInput);

            System.out.println("server says:" + br.readLine());

            if ("exit".equalsIgnoreCase(userInput)) {
                    sock.close();
                    break;
            }
        }
    }
    
}
