package proiectpao;

import java.net.*;
import java.io.*;

public class Server
{
    static final int PORT = 5008;
    
    static protected ServerSocket ss = null;
    protected Socket cs = null;
   
    static protected int threads;
    
    Server() throws IOException
    {
        try 
        {
            ss = new ServerSocket(PORT);
        } 
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public void start() throws IOException
    {
        while (true) 
        {
            try 
            {
                cs = ss.accept();
            } 
            catch (IOException e) 
            {
                e.printStackTrace(System.out);
                return;
            }
            // new thread for a client
            new ServerThread(cs, threads++).start();
        }
        /*
        try 
        {
            cs.close();
            ss.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace(System.out);
            return;
        }*/
    }
    
    public static void main( String args[] ) throws IOException
    {
        Server testServer = new Server();
        testServer.start();
    }
}
