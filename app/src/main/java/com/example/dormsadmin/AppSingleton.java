package com.example.dormsadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dormsadmin.Model.Complaint;
import com.example.dormsadmin.Model.MenuSelection;
import com.example.dormsadmin.Model.Room;
import com.example.dormsadmin.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class AppSingleton {

    public static AppSingleton INSTANCE = null;

    public static AppSingleton getINSTANCE() {
        if(INSTANCE == null) INSTANCE = new AppSingleton();
        return INSTANCE;
    }

    Room selectedRoom;

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }


    List<Student> studentList;
    MutableLiveData<String> GLOBAL_LIVE = new MutableLiveData<>();

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
        GLOBAL_LIVE.setValue("STUDENT_LIST_UPDATE");
    }

    public List<Student> newStudentRoomList = new ArrayList<>();
    public List<Student> deletedStudent = new ArrayList<>();

    public void addToStudentList(Student student){
        if(studentList == null) studentList = new ArrayList<>();
        Boolean is_present = false;
        for (Student s : studentList){
            if(s.getStudId().equals(student.getStudId())){
                is_present = true;
                break;
            }
        }

        if(!is_present){
            studentList.add(student);
            newStudentRoomList.add(student);
            GLOBAL_LIVE.setValue("STUDENT_LIST_UPDATE");
        }

    }

    public void deleteFromStudentList(Student student){
        if(studentList == null) studentList = new ArrayList<>();
        studentList.remove(student);
        if(!newStudentRoomList.contains(student))
            deletedStudent.add(student);
        newStudentRoomList.remove(student);
        GLOBAL_LIVE.setValue("STUDENT_LIST_UPDATE");
    }

    public LiveData<String> getGLOBAL_LIVE() {
        return GLOBAL_LIVE;
    }

    Student selectedStudent;

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }


    Complaint selectedComplaint;

    public Complaint getSelectedComplaint() {
        return selectedComplaint;
    }

    public void setSelectedComplaint(Complaint selectedComplaint) {
        this.selectedComplaint = selectedComplaint;
    }

    MenuSelection selectedMenu;

    public MenuSelection getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(MenuSelection selectedMenu) {
        this.selectedMenu = selectedMenu;
    }
}
