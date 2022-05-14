package com.example.dormsadmin.Model;

import java.util.List;

public class Room {

    String name,rent,beds,roomId;
    List<Student> students;

    public Room(String name, String rent, String beds) {
        this.name = name;
        this.rent = rent;
        this.beds = beds;
    }
    public Room() {

    }

    public String getName() {
        return name;
    }

    public String getRent() {
        return rent;
    }

    public String getBeds() {
        return beds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
