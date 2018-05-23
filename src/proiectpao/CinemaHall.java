package proiectpao;

import java.io.Serializable;

public class CinemaHall implements Serializable 
{
    String name, managerName;
    int id, seats, openingTime, closingTime;

    public CinemaHall() {}
    
    public CinemaHall(int id, String name, int seats, String managerName, int openingTime, int closingTime)
    {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.managerName = managerName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public void setId(int id) { this.id = id; }  
    public void setName(String name) { this.name = name; }
    public void setSeats(int seats) { this.seats = seats; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public void setOpeningTime(int openingTime) { this.openingTime = openingTime; }
    public void setClosingTime(int closingTime) { this.closingTime = closingTime; }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getSeats() { return seats; }
    public String getManagerName() { return managerName; }
    public int getOpeningTime() { return openingTime; }
    public int getClosingTime() { return closingTime; }
    
    public void printContent()
    {
        System.out.println( "ID: " + id );
        System.out.println( "NAME: " + name );
        System.out.println( "SEATS: " + seats );
        System.out.println( "MANAGER: " + managerName );
        System.out.println( "OPENING_TIME: " + openingTime );
        System.out.println( "CLOSING_TIME: " + closingTime );
        System.out.println();
    }
}
