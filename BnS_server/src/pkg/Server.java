package pkg;

import pkg.foxoft.bombnshovel.net.Cmd;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

public class Server 
{
  static ArrayList<ClientThread> clientsPending=new ArrayList<>();
  static ArrayList<ClientThread> clientsMatching=new ArrayList<>();
  
  public static void main(String[] args)
  {
    int port=4444;
    
    System.out.println("Waiting for clients.");
    try 
    {
      ServerSocket socketListener=new ServerSocket(port);
      
      while(true)
      {
        //Waiting for new client.
        Socket client=null;
        while(client==null) 
        {client=socketListener.accept();}
        //Waiting for new client.
        
        //Opening new thread for client.
        ClientThread newClient=new ClientThread(client);
        newClient.start();
        clientsPending.add(newClient);
        //Opening new thread for client.
        
        //Pairing two pending clients.
        if (clientsPending.size()>1
        && clientsPending.get(0).isAlive()
        && clientsPending.get(1).isAlive())
        {automatch();}
        //Pairing two pending clients.
      }
    }
    catch(SocketException e)
    {System.err.println("Socket exception");}
    catch (IOException e) 
    {System.err.println("I/O exception");}
  }
  
  /**
   * Pairs two clients and starts game session.
   * @throws IOException 
   */
  private static void automatch() throws IOException
  {  
    System.out.println("Pairing two clients!");
    
    //Removing clients from pending list.
    ClientThread client1=clientsPending.remove(0),
                 client2=clientsPending.remove(0);
    //Removing clients from pending list.
    
    //Pairing clients.
    client1.pair=client2;
    client2.pair=client1;
    //Pairing clients.
    
    //Adding clients in mathing list.
    clientsMatching.add(client1);
    clientsMatching.add(client2);
    //Adding clients in mathing list.
    
    //Generating random seed for map.
    long seed=new Random(System.nanoTime()).nextLong(); 
    //Generating random seed for map.
    
    //Sending seed and team id to clients.
    client1.send(new Cmd("generate",seed,0));
    client2.send(new Cmd("generate",seed,1));
    //Sending seed and team id to clients.
    
  }
  
  /**
   * Disconnects client and terminates his process.
   * @param client 
   */
  public static void disconnect(ClientThread client)
  {
    clientsMatching.remove(client);
    client.terminate();
  }
  
  /**
   * Disconnects pending client and terminates his process.
   * @param client 
   */
  public static void disconnectPending(ClientThread client)
  {
    clientsPending.remove(client);
    client.terminate();
  }
  
}
