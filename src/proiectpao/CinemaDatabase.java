package proiectpao;

import java.sql.*;

public class CinemaDatabase extends Database
{
    static String cinemaDatabaseFileName = "CinemaDatabase.db";
    static String cinemaDatabaseConName  = "jdbc:sqlite:" +  cinemaDatabaseFileName;
    static String  cinemaHallTableName = "CinemaHall";
    static String cinemaHallSql = "CREATE TABLE CinemaHall " +   
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        "NAME           TEXT    NOT NULL, " + 
                        " SEATS            INT     NOT NULL, " +
                        " MANAGER           TEXT    NOT NULL, " + 
                        " OPENING_TIME           INT     NOT NULL, " +
                        " CLOSING_TIME           INT     NOT NULL)"; 
    static int cinemaHallId = 0;
    
    public static void insertCinemaHall(String name, int seats, String managerName, int openingTime, int closingTime)
    {
        Connection c = null;
        Statement stmt = null;
        try
        {
            c = connect(cinemaDatabaseFileName, cinemaDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, cinemaHallTableName);
            if( isCinemaHall(c , name) == false )
            {
                  stmt = c.createStatement();
                  String sql = "INSERT INTO " + cinemaHallTableName + " (ID, NAME, SEATS, MANAGER, OPENING_TIME, CLOSING_TIME) " +
                               "VALUES ( " + cinemaHallId + ", '" + name + "', " + seats+", '" + 
                                managerName + "', " + openingTime + ", " + closingTime + " )";
                  stmt.executeUpdate(sql);
                  stmt.close();
                  System.out.println("Cinema hall created successfully");
           }
           else
           {
                 System.out.println("Cinema hall already exists");
           }
           cinemaHallId++;
           c.commit();
           c.close();
        }
        catch ( Exception e ) 
        {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static boolean isCinemaHall(Connection c, String cinemaHallName) throws SQLException 
    {
        try  
        {
            Statement stmt = c.createStatement();
            ResultSet cinemaHall = stmt.executeQuery("SELECT * FROM " + cinemaHallTableName + " WHERE NAME = '" + cinemaHallName + "';" );
            if (cinemaHall.next()) 
            {
                cinemaHall.close();
                stmt.close();
                return true;
            }
            cinemaHall.close();
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
    
    public static CinemaHall[] viewCinemaHalls()
    {
        Connection c = null;
        Statement stmt = null; 
        try 
        {
            c = connect(cinemaDatabaseFileName, cinemaDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, cinemaHallTableName);
            stmt = c.createStatement();
            CinemaHall[] result = new CinemaHall[cinemaHallId];
            ResultSet cinemaHalls = stmt.executeQuery( "SELECT * FROM " + cinemaHallTableName + ";" );
            while ( cinemaHalls.next() ) 
            {
                int id = cinemaHalls.getInt("ID");
                String  name = cinemaHalls.getString("NAME");
                int seats = cinemaHalls.getInt("SEATS");
                String  managerName = cinemaHalls.getString("MANAGER");
                int openingTime = cinemaHalls.getInt("OPENING_TIME");
                int closingTime = cinemaHalls.getInt("CLOSING_TIME");
                
                CinemaHall temp = new CinemaHall();
                temp.setId(id);
                temp.setName(name);
                temp.setSeats(seats);
                temp.setManagerName(managerName);
                temp.setOpeningTime(openingTime);
                temp.setClosingTime(closingTime);
                result[id] = temp;
            }
            cinemaHalls.close();
            stmt.close();
            c.close();
            System.out.println("Cinema halls displayed successfully");
            return result;
        } 
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return null;
        }
    }
    
    public static void deleteCinemaHall(String cinemaHallName)
    {
        try 
        {
            Connection c = connect(cinemaDatabaseFileName, cinemaDatabaseConName);
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = "DELETE from " + cinemaHallTableName + " WHERE NAME='" + cinemaHallName + "';";    
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
        System.out.println("Cinema hall deleted successfully");
    }
    
    public static void modifyCinemaHall(String cinemaHallName, String field, String value)
    {
        try 
        {
            Connection c = connect(cinemaDatabaseFileName, cinemaDatabaseConName);
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = "UPDATE " + cinemaHallTableName + " SET " + field + "='" + value + "' WHERE NAME='" + cinemaHallName + "';";    
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
        System.out.println("Cinema hall successfully modified");
    }
    
    public static void refreshId(Connection c, String tableName) throws SQLException
    {
        try 
        {
            Statement stmt = c.createStatement(); 
            ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName + " ORDER BY ID DESC LIMIT 1;");
            if (res.next())
            {
                cinemaHallId = res.getInt("ID") + 1;
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
    
    public static void main( String args[] ) 
    {
        //createTable(cinemaDatabaseFileName, cinemaDatabaseConName, cinemaHallTableName, cinemaHallSql);//creeaza baza de date(nu trb rulat decat o data ca sa genereze fisierul .db)
        insertCinemaHall("Pompeiu", 100, "John Petrescu", 4, 6);
//        insertCinemaHall("Titeica", 120, "Cezar Ionescu", 6, 8);
//        insertCinemaHall("Haret", 200, "Lolita Lola", 2, 4);
//        insertCinemaHall("Stoilow", 50, "Dan Puric", 12, 2);
//        deleteCinemaHall("Titeica");
//        modifyCinemaHall("Pompeiu", "SEATS", "30");
//        CinemaHall[] result = viewCinemaHalls();
//        for (CinemaHall temp:result)
//            if(temp != null)
//                temp.printContent();
    }
}
