package proiectpao;

import java.sql.*;
import java.util.Random;
import java.util.Arrays;

public class UsersDatabase extends Database
{
    static String usersDatabaseFileName = "UsersDatabase.db";
    static String usersDatabaseConName  = "jdbc:sqlite:" +  usersDatabaseFileName;
    static String  userTableName = "User";
    static String userSql = "CREATE TABLE User " +   
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        "NAME           TEXT    NOT NULL, " +
                        "SALT           INT    NOT NULL, " +
                        "PASSWORD          INT    NOT NULL)";  
    static int userId = 0;
    
    public static void insertUser(String name, String password)
    {
        Connection c = null;
        Statement stmt = null;
        try
        {
            c = connect(usersDatabaseFileName, usersDatabaseConName);
            c.setAutoCommit(false);
            refreshId(c, userTableName);
            if( isUser(c , name) == false )
            {
                  stmt = c.createStatement();
                  Random randomGenerator = new Random();
                  int salt = randomGenerator.nextInt(2147483647);
                  String saltString = Integer.toString(salt);
                  password+=saltString;
                  String[] passwordArray = password.split("");
                  int hash = Arrays.hashCode(passwordArray);
                  String sql = "INSERT INTO " + userTableName + " (ID, NAME, SALT, PASSWORD) " +
                               "VALUES ( " + userId + ", '" + name + "', " + salt + ", "  + hash + " )";
                  stmt.executeUpdate(sql);
                  stmt.close();
                  System.out.println("User created successfully");
           }
           else
           {
                 System.out.println("User already exists");
           }
           userId++;
           c.commit();
           c.close();
        }
        catch ( Exception e ) 
        {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static boolean isUser(Connection c, String userName) throws SQLException 
    {
        try  
        {
            Statement stmt = c.createStatement();
            ResultSet user = stmt.executeQuery("SELECT * FROM " + userTableName + " WHERE NAME = '" + userName + "';" );
            if (user.next()) 
            {
                user.close();
                stmt.close();
                return true;
            }
            user.close();
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
    
    public static void refreshId(Connection c, String tableName) throws SQLException
    {
        try 
        {
            Statement stmt = c.createStatement(); 
            ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName + " ORDER BY ID DESC LIMIT 1;");
            if (res.next())
            {
                userId = res.getInt("ID") + 1;
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
    
    public static boolean isValid(String userName, String password) throws SQLException 
    {
        Connection c = null;
        Statement stmt = null;
        try  
        {
            boolean isValid = false;
            c = connect(usersDatabaseFileName, usersDatabaseConName);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + userTableName + " WHERE NAME = '" + userName + "';";
            ResultSet user = stmt.executeQuery(sql);
            if (user.next()) 
            {
                int salt = user.getInt("SALT");
                c.commit();
                int hash = user.getInt("PASSWORD");
                String saltString = Integer.toString(salt);
                password+=saltString;
                String[] passwordArray = password.split("");
                int computedHash = Arrays.hashCode(passwordArray);
                if(computedHash == hash)
                {
                    System.out.println("Welcome!");
                    isValid = true;
                }
            }
            stmt.executeUpdate(sql);
            c.commit();
            user.close();
            stmt.close();
            c.close();
            if(!isValid)
                System.out.println("The User Name or Password is Incorrect!");
            return isValid;
        }
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return false;
        }
    }
    
    public static void main( String args[] ) throws SQLException 
    {
        createTable(usersDatabaseFileName, usersDatabaseConName, userTableName, userSql);//creeaza baza de date(nu trb rulat decat o data ca sa genereze fisierul .db)
        insertUser("Marian", "Kafku");
        insertUser("Gabi", "Kafku");
        //isValid("Marial", "Kafku");
        //isValid("Marian", "Kafkuu");
        //isValid("Marian", "Kafku");
    }    
}
