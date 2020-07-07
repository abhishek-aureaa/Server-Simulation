// File Name GreetingServer.java
package your_package;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
//class NodeTree;
//import .\NodeTree;

public class NodeTree {
	
   private int val;
   public NodeTree NTLeft;
   public  NodeTree NTRight;
   public int value;
   public Timestamp timestamp;// = new Timestamp(System.currentTimeMillis());
 
   public NodeTree(int N, Timestamp timestamp1)	
   {
	   value = N;
	   NTLeft = null;
	   NTRight = null;
	   timestamp  = timestamp1;
   }
   
   public int getNodeTreeValue()
   {
	   return value;
   }
   public Timestamp getNodeTreeTimestamp()
   {
	   return timestamp;
   }
   
}