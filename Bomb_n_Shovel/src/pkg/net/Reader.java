package pkg.net;

import java.net.*;
import java.io.*;

/**
 * Reads objects from server.
 */
public class Reader extends Thread
{
  Socket socket;
  public boolean running=true;
  private Object last;
  
  
  Reader(Socket socket_arg)
  {
    socket=socket_arg;
    setDaemon(true);
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
          last=lastBuf;
        }
      }
    }
    catch(ClassNotFoundException|IOException  e)
    {System.out.println("fak");}
  }
  

  
  public boolean isReceived()
  {return last!=null;}
  
  
  
  public Object read()
  {
    Object obj=last;
    last=null;
    return obj;
  }
}
