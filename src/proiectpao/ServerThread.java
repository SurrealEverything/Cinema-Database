package proiectpao;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerThread
{
    protected Socket cs;
    
    protected int identity = 0; 
    
    DataInputStream dis = null;
    ObjectOutputStream oos = null;
        
    public ServerThread(Socket cs, int i) 
    {
        this.cs = cs;
        identity = i; 
    }

    public void start() throws IOException
    {
        try 
        {
            dis = new DataInputStream(cs.getInputStream());
            oos = new ObjectOutputStream(cs.getOutputStream());
        }
        catch (IOException e) 
        {
            e.printStackTrace(System.out);
            return;
        }
        String line;
        loop:while (true) 
        {
            try 
            {
                //Mesaj receptionat
                line = (String) dis.readUTF();
                line = line.trim();
                switch(line)
                {
                    case "STOP":
                        receiveStop();
                        break loop; 
                    case "CINSERT":
                        receiveInsertCinemaHall();
                        break;
                    case "CVIEW":
                        receiveViewCinemaHalls();
                        break;  
                    case "CDELETE":
                       receiveDeleteCinemaHall();
                       break;  
                    case "CMODIFY":
                       receiveModifyCinemaHall();
                       break;  
                    case "TINSERT":
                        receiveInsertTicket();
                        break;
                    case "TVIEW":
                        receiveViewTickets();
                        break;  
                    case "TDELETE":
                       receiveDeleteTicket();
                       break;  
                    case "TMODIFY":
                       receiveModifyTicket();
                       break; 
                    case "TINTERVAL":
                       receiveViewIntervalTickets() ;
                       break;  
                    case "THALL":
                       receiveViewHallTickets();
                       break; 
                }
            } 
            catch (IOException e)
            {
                e.printStackTrace(System.out);
                return;
            }
        }
    }
    
    protected void receiveStop() throws IOException
    {
        cs.close(); 
        dis.close(); 
        oos.close();
    }
    
    protected void receiveInsertCinemaHall() throws IOException
    {
        String name, managerName;
        int openingTime, closingTime, seats;
        try
        {
            name = (String) dis.readUTF();
            seats = (int) dis.readInt();
            managerName = (String) dis.readUTF();
            openingTime = (int) dis.readInt();
            closingTime = (int) dis.readInt();
            CinemaDatabase.insertCinemaHall(name, seats, managerName, openingTime, closingTime);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveViewCinemaHalls() throws IOException
    {
        oos.writeObject(CinemaDatabase.viewCinemaHalls());
    }
    
    protected void receiveDeleteCinemaHall() throws IOException
    {
        String cinemaHallName;
        try
        {
            cinemaHallName = (String) dis.readUTF();
            CinemaDatabase.deleteCinemaHall(cinemaHallName);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveModifyCinemaHall() throws IOException
    {
        String cinemaHallName, field, value;
        try
        {
            cinemaHallName = (String) dis.readUTF();
            field = (String) dis.readUTF();
            value = (String) dis.readUTF();
            CinemaDatabase.modifyCinemaHall(cinemaHallName, field, value);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveInsertTicket() throws IOException
    {
        int hallId, hour, seat;
        String name;
        try
        {
            hallId = (int) dis.readInt();
            hour = (int) dis.readInt();
            seat = (int) dis.readInt();
            name = (String) dis.readUTF();
            TicketsDatabase.insertTicket(hallId, hour, seat, name);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveViewTickets() throws IOException
    {
        oos.writeObject(TicketsDatabase.viewTickets());
    }
    
    protected void receiveDeleteTicket() throws IOException
    {
        String ticketName;
        try
        {
            ticketName = (String) dis.readUTF();
            TicketsDatabase.deleteTicket(ticketName);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveModifyTicket() throws IOException
    {
        String ticketName, field;
        int value;
        try
        {
            ticketName = (String) dis.readUTF();
            field = (String) dis.readUTF();
            value = (int) dis.readInt();
            TicketsDatabase.modifyTicket(ticketName, field, value);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveViewIntervalTickets() throws IOException
    {
        int a, b;
        try
        {
            a = (int) dis.readInt();
            b = (int) dis.readInt();
            oos.writeObject(TicketsDatabase.viewIntervalTickets(a, b));
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    protected void receiveViewHallTickets() throws IOException
    {
        int hall;
        try
        {
            hall = (int) dis.readInt();
            oos.writeObject(TicketsDatabase.viewHallTickets(hall));
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
}
