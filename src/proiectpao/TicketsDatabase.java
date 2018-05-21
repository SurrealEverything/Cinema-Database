package proiectpao;

import java.sql.*;

public class TicketsDatabase extends Database
{
    static String ticketsDatabaseFileName = "TicketsDatabase.db";
    static String ticketsDatabaseConName  = "jdbc:sqlite:" +  ticketsDatabaseFileName;
    static String  ticketsTableName = "Tickets";
    static String ticketsSql = "CREATE TABLE Tickets " +   
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " HALL_ID            INT     NOT NULL, " +
                        " HOUR           INT     NOT NULL, " +
                        " SEAT            INT     NOT NULL, " +
                        " NAME          TEXT    NOT NULL)"; 
    static int ticketsId = 0;
    
    public static void insertTicket(int hallId, int hour, int seat, String name)
    {
        Connection c = null;
        Statement stmt = null;
        try
        {
            c = connect(ticketsDatabaseFileName, ticketsDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, ticketsTableName);
            if( isTicket(c , hallId, hour, seat, name) == false )
            {
                  stmt = c.createStatement();
                  String sql = "INSERT INTO " + ticketsTableName + " (ID, HALL_ID, HOUR, SEAT, NAME) " +
                               "VALUES ( " + ticketsId + ", "+ hallId + ", " + hour + ", " + seat+", '" + name + "' )";
                  stmt.executeUpdate(sql);
                  stmt.close();
                  System.out.println("Ticket added successfully");
           }
           else
           {
                 System.out.println("Ticket already exists");
           }
           ticketsId++;
           c.commit();
           c.close();
        }
        catch ( Exception e ) 
        {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static boolean isTicket(Connection c, int hallId, int hour, int seat, String name) throws SQLException 
    {
        try  
        {
            Statement stmt = c.createStatement();
            ResultSet ticket = stmt.executeQuery("SELECT * FROM " + ticketsTableName + 
            " WHERE HALL_ID = " + hallId +" AND HOUR = " + hour + " AND SEAT = " + seat + " AND NAME='" + name + "';");
            if (ticket.next()) 
            {
                ticket.close();
                stmt.close();
                return true;
            }
            ticket.close();
            stmt.close();
            return false;
        }
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return false;
        }
    }
    
    public static Ticket[] viewTickets()
    {
        Connection c = null;
        Statement stmt = null; 
        try 
        {
            c = connect(ticketsDatabaseFileName, ticketsDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, ticketsTableName);
            stmt = c.createStatement();
            Ticket[] result = new Ticket[ticketsId];
            ResultSet tickets = stmt.executeQuery( "SELECT * FROM " + ticketsTableName + ";" );
            while ( tickets.next() ) 
            {
                int id = tickets.getInt("ID");
                int hallId = tickets.getInt("HALL_ID");
                int hour = tickets.getInt("HOUR");
                int seat = tickets.getInt("SEAT");
                String  name = tickets.getString("NAME");
      
                Ticket temp = new Ticket();
                temp.setId(id);
                temp.setHallId(hallId);
                temp.setHour(hour);
                temp.setSeat(seat);
                temp.setName(name);
                result[id] = temp;
            }
            tickets.close();
            stmt.close();
            c.close();
            System.out.println("Tickets displayed successfully");
            return result;
        } 
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return null;
        }
    }
    
    public static void deleteTicket(String ticketName)
    {
        try 
        {
            Connection c = connect(ticketsDatabaseFileName, ticketsDatabaseConName);
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = "DELETE from " + ticketsTableName + " WHERE NAME='" + ticketName + "';";    
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } 
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Ticket deleted successfully");
    }
    
    public static void modifyTicket(String ticketName, String field, int value)
    {
        try 
        {
            Connection c = connect(ticketsDatabaseFileName, ticketsDatabaseConName);
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = "UPDATE " + ticketsTableName + " SET " + field + "='" + value + "' WHERE NAME='" + ticketName + "';";    
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } 
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Ticket successfully modified");
    }
    
    public static void refreshId(Connection c, String tableName) throws SQLException
    {
        try 
        {
            Statement stmt = c.createStatement(); 
            ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName + " ORDER BY ID DESC LIMIT 1;");
            if (res.next())
            {
                ticketsId = res.getInt("ID") + 1;
                c.commit();
            }
            stmt.close();
        } 
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Id refreshed successfully!");
    }
    
    public static Ticket[] viewIntervalTickets(int a, int b)
    {
        Connection c = null;
        Statement stmt = null; 
        try 
        {
            c = connect(ticketsDatabaseFileName, ticketsDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, ticketsTableName);
            stmt = c.createStatement();
            Ticket[] result = new Ticket[ticketsId];
            ResultSet tickets = stmt.executeQuery( "SELECT * FROM " + ticketsTableName + " WHERE HOUR>=" + a + " AND HOUR<=" + b + ";" );
            while ( tickets.next() ) 
            {
                int id = tickets.getInt("ID");
                int hallId = tickets.getInt("HALL_ID");
                int hour = tickets.getInt("HOUR");
                int seat = tickets.getInt("SEAT");
                String  name = tickets.getString("NAME");
      
                Ticket temp = new Ticket();
                temp.setId(id);
                temp.setHallId(hallId);
                temp.setHour(hour);
                temp.setSeat(seat);
                temp.setName(name);
                result[id] = temp;
            }
            tickets.close();
            stmt.close();
            c.close();
            System.out.println("Tickets displayed successfully");
            return result;
        } 
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return null;
        }
    }
    
    public static Ticket[] viewHallTickets(int hall)
    {
        Connection c = null;
        Statement stmt = null; 
        try 
        {
            c = connect(ticketsDatabaseFileName, ticketsDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, ticketsTableName);
            stmt = c.createStatement();
            Ticket[] result = new Ticket[ticketsId];
            ResultSet tickets = stmt.executeQuery( "SELECT * FROM " + ticketsTableName + " WHERE HALL_ID=" + hall + ";" );
            while ( tickets.next() ) 
            {
                int id = tickets.getInt("ID");
                int hallId = tickets.getInt("HALL_ID");
                int hour = tickets.getInt("HOUR");
                int seat = tickets.getInt("SEAT");
                String  name = tickets.getString("NAME");
      
                Ticket temp = new Ticket();
                temp.setId(id);
                temp.setHallId(hallId);
                temp.setHour(hour);
                temp.setSeat(seat);
                temp.setName(name);
                result[id] = temp;
            }
            tickets.close();
            stmt.close();
            c.close();
            System.out.println("Tickets displayed successfully");
            return result;
        } 
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return null;
        }
    }
    
    public static void main( String args[] ) 
    {
        createTable(ticketsDatabaseFileName, ticketsDatabaseConName, ticketsTableName, ticketsSql);//idem
        /*insertTicket(1, 12, 22, "Petru");
        insertTicket(1, 12, 23, "Stefan");
        insertTicket(2, 4, 60, "Gigi");
        insertTicket(1, 6, 23, "Marius Ifrim");
        insertTicket(2, 8, 20, "Marian");
        deleteTicket("Stefan");
        modifyTicket("Petru", "SEAT", 50);
        Ticket[] result = viewTickets();
        //Ticket[] result = viewIntervalTickets(4, 8);//vezi bilete intr-o data
        //Ticket[] result = viewHallTickets(2);//vezi bilete intr-o sala
        for (Ticket temp:result)
            if(temp != null)
                temp.printContent();*/
    }
}
