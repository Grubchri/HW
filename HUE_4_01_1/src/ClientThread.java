
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
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        Driver myDriver = new oracle.jdbc.driver.OracleDriver();
        try {
            DriverManager.registerDriver(myDriver);
        } catch (SQLException ex) {
           System.out.println(ex);
        }
        try {
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:System/rgrh23@Grubchri:1521:XE");
            stm=conn.createStatement();
        
            int ctr=0;
            int temp=0;
            String wt=" ";
            String name=" ";
            
            try{
                temp=0;
                wt=" ";
                name=" ";
                PrintWriter co=new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()));
                co.println("Connected.");
                co.println("instructions: use Name=<Weaterstation>, Weathertype=<Weathertype> or Temperature=<Temperature>\n the last submitted values will be used in the dbs");
                co.println("use Analyse like: Analyse=<Weatherstationname>");
                while(!clientSocket.isClosed()){
                    if(ctr==0){
                        ctr++;
                        co.println("To Exit write Exit");
                    }

                    Thread.sleep(100);
                    BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    co.flush();

                    String str=" ";
                    str=clientIn.readLine();
                    
                    System.out.println(str);
                    
                    if(str.toUpperCase().contains("EXIT")){
                        clientSocket.close();
                    }

                    if(str.toUpperCase().contains("WEATHERTYPE".toUpperCase()+"=")){
                        wt=SplitString(str);
                    }

                    if(str.toUpperCase().contains("Name".toUpperCase()+"=")){
                        name=SplitString(str);
                    }

                    if(str.toUpperCase().contains("Temperature".toUpperCase()+"=")){
                        temp=Integer.parseInt(SplitString(str));
                    }
                    if(str.toUpperCase().contains("ANALYSE".toUpperCase()+"=")){
                       String res= Analyse(str);
                       co.println("Average: "+res);
                    }
                    if(str.toUpperCase().contains("HELP".toUpperCase())){
                        co.println("instructions: use Name=<Weaterstation>, Weathertype=<Weathertype> or Temperature=<Temperature>\n the last submitted values will be used in the dbs");
                        co.println("use Analyse like: Analyse=<Weatherstationname>");
                    }
                    
                    
                    
                }
                if(!(name.equals(" ") && wt.equals(" "))){
                    stm.executeUpdate("Insert into measurements Values('"+name+"','"+wt+"',"+temp+")");
                }
                System.out.println(temp);
                conn.commit();
                
                String res= Analyse(name);
                co.println("Average: "+res);
                
                conn.close();

            }catch(IOException ex){
                System.out.println(ex);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            } catch (SQLException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private String SplitString(String str) {
        String ret="";
        int index=0;
        
        for(int i=0;i<str.length();i++){
            char ch [] =str.toCharArray();
            if(ch[i]=='='){
                index=i+1;
            }
        }
        
        ret=str.substring(index);
            
        return ret;
    }

    public String Analyse(String str) throws SQLException,IOException {
        Connection conn=DriverManager.getConnection("jdbc:oracle:thin:System/rgrh23@Grubchri:1521:XE");
        stm=conn.createStatement();
        
        int avg=0;
        int ctr=0;
        String test="";
        ResultSet rs;
        
        rs=stm.executeQuery("Select * from measurements where Servername='"+SplitString(str)+"'");
        
        while(rs.next()){
            avg+=rs.getInt("Temperature");
            System.out.println(rs.getInt("Temperature"));
            ctr++;
        }
        
        int r=(avg/ctr);
        String res=Integer.toString(r);
        
        return res;
    }
    
}
