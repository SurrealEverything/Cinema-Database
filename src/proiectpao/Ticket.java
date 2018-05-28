package proiectpao;

import java.io.Serializable;

public class Ticket implements Serializable 
{
    int id, hallId, hour, seat;
    String name;

    public Ticket() {}
    
    public Ticket(int id, int hallId, int hour, int seat, String name)
    {
        this.id = id;
        this.hallId = hallId;
        this.hour = hour;
        this.seat = seat;
        this.name = name;
    }
    
    public void setId(int id) { this.id = id; }
    public void setHallId(int hallId) { this.hallId = hallId; }
    public void setHour(int hour) { this.hour = hour; }
    public void setSeat(int sit) { this.seat = sit; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }
    public int getHallId() { return hallId; }
    public int getHour() { return hour; }
    public int getSeat() { return seat; }
    public String getName() { return name; }
    
    public void printContent()
    {
        System.out.println( "ID: " + id );
        System.out.println( "HALL_ID: " + hallId );
        System.out.println( "HOUR: " + hour );
        System.out.println( "SEAT: " + seat );
        System.out.println( "NAME: " + name );
        System.out.println();
    }
    
    public String returnContent()
    {
        String result = "";
        result += "ID: " + id + "<br/>";
        result += "HALL_ID: " + hallId + "<br/>";
        result += "HOUR: " + hour + "<br/>";
        result += "SEAT: " + seat + "<br/>";
        result += "NAME: " + name + "<br/>";
        result += "<br/>";
        return result;
    }
}