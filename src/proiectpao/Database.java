package proiectpao;

import java.sql.*;

public class Database
{ 
    public static Connection connect(String databaseFileName, String databaseConName)
    {
        Connection c = null;
        try 
        {
                  Class.forName("org.sqlite.JDBC");
                  c = DriverManager.getConnection( databaseConName );
                  System.out.println("Connected to database \'" + databaseFileName + "\' successfully!");
        } 
        catch ( Exception e ) 
        {
                  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                  System.exit(0);
        }
        return c;
    }
    
    public static boolean isTable(Connection c, String tableName) throws SQLException 
    {
        try (ResultSet tables = c.getMetaData().getTables(null, null, tableName, null)) 
        {
            while (tables.next()) 
            {
                String name = tables.getString("TABLE_NAME");
                if (name != null && name.equals(tableName)) 
                    return true;
            }
        }
        return false;
    }
    
    public static void createTable(String databaseFileName, String databaseConName, String tableName, String tableSql)  
    {
            Connection c = null;
            Statement stmt = null;
            try 
            {
                c = connect(databaseFileName, databaseConName);
                if( isTable(c , tableName) == false )
                {
                    stmt = c.createStatement();
                    stmt.executeUpdate(tableSql);
                    stmt.close();
                    c.close();
                    System.out.println("Table \'" + tableName + "\' created successfully");
                }
                else
                {
                    System.out.println("Table \'" + tableName + "\' already exists!");
                }
            } 
            catch ( Exception e ) 
            {
                  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                  System.exit(0);
            }

    }

}
