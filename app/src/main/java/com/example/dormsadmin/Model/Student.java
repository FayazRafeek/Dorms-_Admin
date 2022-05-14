package com.example.dormsadmin.Model;

public class Student {
    String profileUrl;
    String studId,full_name,phone,address,email,gname,gphonenumber;
    String adminId;

    String college,password;

    public Student(String full_name, String phone, String address, String email, String gname, String gphonenumber){
        this.full_name = full_name;
        this.phone = phone;
        this.address = address;
        this.email= email;
        this.gname = gname;
        this.gphonenumber= gphonenumber;
    }

    public Student(String studId, String full_name, String phone) {
        this.studId = studId;
        this.full_name = full_name;
        this.phone = phone;
    }

    public Student() {
        
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGphonenumber() {
        return gphonenumber;
    }

    public void setGphonenumber(String gphonenumber) {
        this.gphonenumber = gphonenumber;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
