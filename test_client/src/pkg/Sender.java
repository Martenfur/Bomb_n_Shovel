package pkg;

import java.io.*;
import java.net.*;

public class Sender implements AutoCloseable, Closeable
{
  Socket socket;
  public Sender(Socket socket_arg) throws IOException,UnknownHostException
  {socket=socket_arg;}
  
  public void send(Object obj) throws IOException
  {
    ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
    out.writeObject(obj);
    out.flush();
  }
  
  public Object receive() throws IOException,ClassNotFoundException
  {
    ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
    Object obj=in.readObject();
    return obj;
  }
  
  @Override
  public void close() throws IOException
  {socket.close();}
}
