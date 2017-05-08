package pkg;

import pkg.net.Cmd;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Processes messages from client.
 */
public class ClientThread extends Thread
{
  Socket socket;
  ClientThread pair;
  boolean running=true;

  ClientThread(Socket socket_arg)
  {socket=socket_arg;}
  
  @Override
  public void run()
  {
    System.out.println("Client connected!");
    
    //Waiting for pair client.
    while(pair==null && running)
    {
      try
      {TimeUnit.MILLISECONDS.sleep(100);}
      catch(InterruptedException e)
      {System.out.println("NO SLEEP FOR YA!");}
      
      try
      {
        if (pair==null)
        {
          Sender sender=new Sender(socket);
          sender.send(new Cmd("ping"));
        }
      }
      catch(IOException e)
      {
        System.out.println("CLIENT'S DED!!!");
        Server.disconnectPending(this);
      }
    }
    //Waiting for pair client.
    
    
    
    //Proccessing messages from game session. 
    if (pair!=null)
    {
      //Main session's routine.
      try
      {
        Sender sender=new Sender(socket);
        while(running)
        {
          Object received=sender.receive();
          
          if (received instanceof Cmd)
          {pair.send(received);}
        }
      }
      catch(IOException|ClassNotFoundException e)
      {
        System.out.println("Client disconnected!");
        
        try
        {pair.send(new Cmd("disconnect"));}
        catch(IOException ex)
        {System.out.println("It seems, another client's already disconnected!");}
        
        Server.disconnect(pair);
        Server.disconnect(this);
        
      }
      //Main session's routine.
    }
    //Proccessing messages from game session.
    
  }
  
  /**
   * Sends an object to client.
   * @param obj
   * @throws IOException 
   */
  void send(Object obj) throws IOException
  {
    Sender sender=new Sender(socket);
    sender.send(obj);
  }
  
  /**
   * Terminates process.
   */
  public void terminate()
  {running=false;}

}
