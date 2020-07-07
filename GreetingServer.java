// File Name GreetingServer.java
//package your_package;
package Java;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import your_package.*;
//import youtube.*;
import NewThread.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

public class GreetingServer {
  /**
   * Start the application.
   * 
   * @param args
   *          Command line parameters are not used.
   */
  public static void main(String args[]) {
    WebServer ws = new WebServer();
    ws.start();
  }
}

class WebServer {

   public NodeTree TreeInsert(NodeTree root1, int NT, Timestamp timestamp)
   {
		if (root1 == null)	
		{
		   root1 = new NodeTree(NT,timestamp);
		   return root1;		   
		}
		else if(NT < root1.value) 
		{
			root1.NTLeft = TreeInsert(root1.NTLeft, NT, timestamp);
		}
		else if(NT > root1.value) 
		{
			root1.NTRight = TreeInsert(root1.NTRight, NT, timestamp);
		}		
		return root1;
   };

   public void TreeSearch(NodeTree root2, int NT)
   {
		if (root2 == null)	
		   return;
		else if(NT < root2.value) 
		{
			TreeSearch(root2.NTLeft, NT);
		}
		else if(NT > root2.value) 
		{
			TreeSearch(root2.NTRight, NT);
		}
		else if(NT == root2.value) 
		{
			//POST /kill with arguments connid=1   - implement it here
		}
   };

/*
  public void TreeTraversal(NodeTree root2) //can choose any - I chose inorder
   {
		if(root2.NTLeft != null) 
		{
			TreeTraversal(root2.NTLeft);
		}
		System.out.println("---------------------------------");
		System.out.println("connid : " + root2.getNodeTreeValue());			
		long diff = root2.getNodeTreeTimestamp() - timestamp;
		Timestamp ts = new Timestamp(diff);
		System.out.println("                  TimeLeft : " + ts);
		System.out.println("---------------------------------");		
		if(root2.NTRight != null) 
		{
			TreeTraversal(root2.NTRight);
		}		
	};
*/
  public void TreeTraversal(NodeTree root2) //can choose any - I chose inorder
   {
		if(root2.NTLeft != null) 
		{
			TreeTraversal(root2.NTLeft);
		}
		System.out.println("---------------------------------");
		System.out.println("connid : " + root2.getNodeTreeValue());	
		
		//calculate the time left wrt the current timestamp.
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());		 
		long diff = root2.getNodeTreeTimestamp().getTime() - timestamp.getTime();
		Timestamp ts = new Timestamp(diff);
		System.out.println("                  TimeLeft : " + ts);
		System.out.println("---------------------------------");		
		if(root2.NTRight != null) 
		{
			TreeTraversal(root2.NTRight);
		}		
	};


  /**
   * WebServer constructor.
   */
  protected void start() {
    ServerSocket s;
	NodeTree root = null;
    System.out.println("Webserver starting up on port 80");
    System.out.println("(press ctrl-c to exit)");
    try {
      // create the main server socket
      s = new ServerSocket(80);
	  s.setSoTimeout(0);
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return;
    }

    System.out.println("Waiting for connection");
    for (;;) {
      try {
        // wait for a connection
        Socket remote = s.accept();
        // remote is now the connected socket
		remote.setSoTimeout(0);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(new InputStreamReader(
            remote.getInputStream()));
        //PrintWriter out = new PrintWriter(remote.getOutputStream());

        // read the data sent. We basically ignore it,
        // stop reading once a blank line is hit. This
        // blank line signals the end of the client HTTP
        // headers.
        String str = ".";
        while (!str.equals(""))
		{
          str = in.readLine();
		  if(str.length() >= 10)
		  {
			String substr = str.substring(0,8);			 
			String substr1 = str.substring(9);
			
			if(substr.equals("Referer:"))
			{
				String lastBit = substr1.substring(substr1.lastIndexOf('/'));
				System.out.println("lastBit = " + lastBit);	

				if(lastBit.substring(0,6).equals("/sleep")) //change it to compare "/sleep?timeout="
				{
					System.out.println("INSIDE SLEEP IF CONDITION");
					String sleepStr  = "/sleep?timeout=";
					String timeoutVal = lastBit.substring(sleepStr.length());	
					System.out.println("timeoutVal : " + timeoutVal);
					int ampersandVal = timeoutVal.lastIndexOf('&');
					
					System.out.println("timeoutVal.substring(0,ampersandVal) : " + timeoutVal.substring(0,ampersandVal));
					String timeOutStr = timeoutVal.substring(0,ampersandVal);
					int timeOut = Integer.parseInt(timeOutStr);
					System.out.println("timeOut : " + timeOut);
					String connidStr = lastBit.substring(lastBit.lastIndexOf('=')+1);
					System.out.println(connidStr);
					int connid=Integer.parseInt(connidStr); 
					System.out.println("Connection id : " +connid);
			
					if(root == null)
					{
						root = TreeInsert(root, connid, timestamp);
						if(root != null)
						{
							System.out.println("root is not null");
						}
					}
					else
					{
						System.out.println("i am NOT here");
						//TreeInsert(root, ++connid, timestamp);
						TreeInsert(root, connid, timestamp);						
					}
					//MyThread mt = new MyThread(remote);
					MyThread mt = new MyThread(remote, timeOut);
					mt.start();				
				}				
				else if(lastBit.equals("/server​status")) {
					//We need to call traversal - inorder
					TreeTraversal(root);
				}
		  }
		}

        // Send the response
        // Send the headers
		/*
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        out.println("Server: Bot");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Welcome to the Ultra Mini-WebServer</H2>");
        out.flush();
        remote.close();*/
	}
	} catch (Exception e) {
        System.out.println("Error: " + e);
      }
   }
}
}

