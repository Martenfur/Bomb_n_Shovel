package pkg;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client 
{
  
  public static void main(String[] arg) 
  {
    int serverPort = 1234; 
    String address = "127.0.0.1"; 
    
    try 
    {
      InetAddress ipAddress=InetAddress.getByName(address); //We love object baths, are we?
      Socket socket=new Socket(ipAddress,serverPort);
      System.out.println("Connected!");
      
      String str="";
      Sender sender=new Sender(socket);
      Reader reader=new Reader(socket);
      reader.start(); //Launching new thread.
      
      while(!str.equals("disconnect"))
      {
        Scanner scanIn=new Scanner(System.in);
        str=scanIn.nextLine();
        sender.send(new Cmd(str));
      }
      
    }
    catch(SocketException e)
    {System.err.println("NO CONNECTION!");}
    catch (IOException e) 
    {
      System.err.println("Client I/O exception");
      e.printStackTrace();
    }
  }
  
}