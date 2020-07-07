package NewThread;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import NewThread.*;

public class MyThread extends Thread {
	 
   private Socket serverid;
   int sleepTime1;   
	public MyThread(Socket server, int sleepTime ) {					   
	   serverid = server;
	   sleepTime1 = sleepTime;
	   //serverid.setSoTimeout(0);
   }	   
   public void run() {
   try {
	  //Do we need  mutex here till end of try
	  //Thread.sleep(5000);
	  serverid.setSoTimeout(0);
	  Thread.sleep(sleepTime1*1000);
	  
	  PrintWriter out = new PrintWriter(serverid.getOutputStream());	  
      out.println("HTTP/1.0 200 OK");
      out.println("Content-Type: text/html");
      out.println("Server: Bot");
      // this blank line signals the end of the headers
      out.println("");
      // Send the HTML page
      out.println("<H1>Welcome to the Ultra Mini-WebServer</H2>");
      out.flush();
      //serverid.close();
	  
	  System.out.println("Thread slept");
      System.out.println("Just connected to " + serverid.getRemoteSocketAddress());
      DataInputStream in = new DataInputStream(serverid.getInputStream());
            
      System.out.println(in.readUTF());
      DataOutputStream out1 = new DataOutputStream(serverid.getOutputStream());
      out1.writeUTF("Thank you for connecting to " + serverid.getLocalSocketAddress()+ "\nGoodbye!");
	  serverid.close();
	  //Do we need mutex till here
	}
	catch (java.net.SocketException s) {
       System.out.println("Socket timed out!");
       //break;
    } 
	catch(InterruptedException ex)
	{
		Thread.currentThread().interrupt();
		//break;
    }
	
    catch (SocketTimeoutException s) {
       System.out.println("Socket timed out!");
       //break;
    } 
	catch (IOException e) {
            e.printStackTrace();
            //break;
    }	
  }
}