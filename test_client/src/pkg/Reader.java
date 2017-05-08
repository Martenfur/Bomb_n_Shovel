package pkg;

import java.net.*;
import java.io.*;

/**
 * Reads objects from server.
 */
public class Reader extends Thread
{
  Socket socket;

  private Object last;
  
  Reader(Socket socket_arg)
  {socket=socket_arg;}
  
  
  @Override
  public void run() 
  {
    System.out.println("Waiting for messages!");  
    try
    {
      Sender sender=new Sender(socket);
      while(true)
      {
        last=sender.receive();
        if (((Cmd)last).get().equals("disconnect"))
        {System.out.println("Lost connection!");}
        else
        {System.out.println("Received an object! It says: "+((Cmd)last).get());}  
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
