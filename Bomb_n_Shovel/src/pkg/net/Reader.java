package pkg.net;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 * Reads objects from server.
 */
public class Reader extends Thread
{
  Socket socket;
  public boolean running=true;
  private LinkedList<Object> objects; //Commmand buffer.
  
  Reader(Socket socket_arg)
  {
    socket=socket_arg;
    setDaemon(true);
    objects=new LinkedList<>();
  }
  
  @Override
  public void run() 
  {
    try
    {
      Sender sender=new Sender(socket);
      while(running)
      {
        Cmd lastBuf=(Cmd)sender.receive();
        if (!lastBuf.get().equals("ping"))
        {
          System.out.println("Received an object! It says: "+((Cmd)lastBuf).get());
          objects.addLast(lastBuf);
        }
      }
    }
    catch(ClassNotFoundException|IOException  e)
    {System.out.println("fak");}
  }
  

  
  public boolean isReceived()
  {return !objects.isEmpty();}
  
  
  
  public Object read()
  {return objects.pollFirst();}
  
  
  
  public Object watch()
  {return objects.peekFirst();}
}
