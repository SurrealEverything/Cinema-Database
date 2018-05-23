package proiectpao;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client
{
    protected InetAddress ADDRESS;
    protected int PORT = 5008;
        
    protected Socket cs;
    
    protected ObjectInputStream ois = null;
    protected DataOutputStream dos = null;
    
    public Client() throws IOException
    {
        try
        {
            ADDRESS = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace(System.out);
            return;
        }
        try 
        {
            cs = new Socket(ADDRESS , PORT); 
        }
        catch(Exception e) 
        {
            e.printStackTrace(System.out);
            return;
        }
    }

    public void start() throws IOException
    {
        Scanner sc = new Scanner(System.in);
        try 
        {
            ois = new ObjectInputStream(cs.getInputStream());
            dos = new DataOutputStream(cs.getOutputStream());
        }
        catch (IOException e) 
        {
            e.printStackTrace(System.out);
            return;
        }
        //Testing Connection
       /*tring line;
        loop:while (true) 
        {
            try 
            {
                System.out.print("CLIENT:Mesaj de trimis: ");
                line = sc.nextLine();
                line = line.trim();
                switch(line)
                {
                    case "STOP":
                        sendStop();
                        break loop; 
                    case "CINSERT":
                        sendInsertCinemaHall("Haret", 200, "Cezar Beneguia", 2, 4);
                        break;
                    case "CVIEW":
                        CinemaHall[] result = sendViewCinemaHalls();
                        for (CinemaHall temp:result)
                            if(temp != null)
                                temp.printContent();
                        break;  
                    case "CDELETE":
                        sendDeleteCinemaHall("Titeica");
                        break;
                    case "CMODIFY":
                        sendModifyCinemaHall("Pompeiu", "SEATS", "30");
                        break;
                    case "TINSERT":
                        sendInsertTicket(1, 12, 23, "Stefan");
                        break;
                    case "TVIEW":
                        Ticket[] resultT = sendViewTickets();
                        for (Ticket temp:resultT)
                            if(temp != null)
                                temp.printContent();
                        break;  
                    case "TDELETE":
                        sendDeleteTicket("Stefan");
                        break;
                    case "TMODIFY":
                        sendModifyTicket("Petru", "SEAT", 50);
                        break;
                    case "TINTERVAL":
                        sendViewIntervalTickets(4, 8);
                        break;
                    case "THALL":
                        sendViewHallTickets(2);
                        break;
                }
            } 
            catch (IOException e)
            {
                e.printStackTrace(System.out);
                return;
            }
        }*/
    }
    
    public void sendStop() throws IOException//opreste clientul si threadul asociat lui
    {
        try 
        {
            dos.writeUTF("STOP");
            cs.close(); 
            ois.close(); 
            dos.close();
        }  
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public void sendInsertCinemaHall(String name, int seats, String managerName, int openingTime, int closingTime) throws IOException
    {
        try
        {
            dos.writeUTF("CINSERT");
            dos.writeUTF(name);
            dos.writeInt(seats);
            dos.writeUTF(managerName);
            dos.writeInt(openingTime);
            dos.writeInt(closingTime);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public CinemaHall[] sendViewCinemaHalls() throws IOException
    {
        try 
        {
            dos.writeUTF("CVIEW");
            CinemaHall[] result = (CinemaHall[]) ois.readObject();
            return result;
        }  
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    public void sendDeleteCinemaHall(String cinemaHallName) throws IOException
    {
        try 
        {
            dos.writeUTF("CDELETE");
            dos.writeUTF(cinemaHallName);
        }  
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public void sendModifyCinemaHall(String cinemaHallName, String field, String value)  throws IOException
    {
        try 
        {
            dos.writeUTF("CMODIFY");
            dos.writeUTF(cinemaHallName);
            dos.writeUTF(field);
            dos.writeUTF(value);
        }  
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public void sendInsertTicket(int hallId, int hour, int seat, String name) throws IOException
    {
        try
        {
            dos.writeUTF("TINSERT");
            dos.writeInt(hallId);
            dos.writeInt(hour);
            dos.writeInt(seat);
            dos.writeUTF(name);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public Ticket[] sendViewTickets() throws IOException
    {
        try 
        {
            dos.writeUTF("TVIEW");
            Ticket[] result = (Ticket[]) ois.readObject();
            return result;
        }  
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    public void sendDeleteTicket(String ticketName) throws IOException
    {
        try 
        {
            dos.writeUTF("TDELETE");
            dos.writeUTF(ticketName);
        }  
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public void sendModifyTicket(String ticketName, String field, int value)  throws IOException
    {
        try 
        {
            dos.writeUTF("TMODIFY");
            dos.writeUTF(ticketName);
            dos.writeUTF(field);
            dos.writeInt(value);
        }  
        catch (IOException e)
        {
            e.printStackTrace(System.out);
            return;
        }
    }
    
    public Ticket[] sendViewIntervalTickets(int a, int b) throws IOException
    {
        try 
        {
            dos.writeUTF("TINTERVAL");
            dos.writeInt(a);
            dos.writeInt(b);
            Ticket[] result = (Ticket[]) ois.readObject();
            return result;
        }  
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    public Ticket[] sendViewHallTickets(int hall) throws IOException
    {
        try 
        {
            dos.writeUTF("THALL");
            dos.writeInt(hall);
            Ticket[] result = (Ticket[]) ois.readObject();
            return result;
        }  
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    public static void main( String args[] ) throws IOException
    {
        Client testClient = new Client();
        testClient.start();
    }
}
