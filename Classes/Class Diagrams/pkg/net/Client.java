package pkg.net;

import java.net.*;
import java.io.*;

public final class Client
{
  public int port=4444; 
  public String ip="127.0.0.1"; 
  //public String ip="77.37.130.93"; 
  public Socket socket;  
  public Reader reader;
  public Sender sender;
  
  public boolean connected;
  
  public Client()
  {connected=connect();}
  
  public boolean connect()
  {
    try 
    {
      InetAddress ipAddress=InetAddress.getByName(ip); //We love object baths, are we?
      socket=new Socket(ipAddress,port);
      
      sender=new Sender(socket);
      reader=new Reader(socket);
      reader.start(); //Launching new thread.
      return true;
    }
    catch(IOException e)
    {
      System.err.println("NO CONNECTION!");
      return false;
    }
  }
  
  public void disconnect()
  {
    if (connected)
    {
      reader.running=false;
      try
      {socket.close();}
      catch(IOException e)
      {System.err.println("SOCKET ERROR!");}
      connected=false;
    }
  }
  
}