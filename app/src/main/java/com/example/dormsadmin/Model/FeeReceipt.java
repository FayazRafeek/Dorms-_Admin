package com.example.dormsadmin.Model;

public class FeeReceipt {

    String payId;
    String studId, studentName;
    String adminId;
    String month, year;
    String roomId;
    Integer rAmt, mAmt, tAmt;
    String ts;


    public FeeReceipt() {
    }

    public FeeReceipt(String payId, String studId, String studentName, String adminId, String month, String year, String roomId, Integer rAmt, Integer mAmt, Integer tAmt, String ts) {
        this.payId = payId;
        this.studId = studId;
        this.studentName = studentName;
        this.adminId = adminId;
        this.month = month;
        this.year = year;
        this.roomId = roomId;
        this.rAmt = rAmt;
        this.mAmt = mAmt;
        this.tAmt = tAmt;
        this.ts = ts;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getrAmt() {
        return rAmt;
    }

    public void setrAmt(Integer rAmt) {
        this.rAmt = rAmt;
    }

    public Integer getmAmt() {
        return mAmt;
    }

    public void setmAmt(Integer mAmt) {
        this.mAmt = mAmt;
    }

    public Integer gettAmt() {
        return tAmt;
    }

    public void settAmt(Integer tAmt) {
        this.tAmt = tAmt;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
