package com.example.dormsadmin.Model;

public class Admin {

    String adminId,name, password,email,phone;

    public Admin() {
    }

    public Admin(String adminId, String name, String password, String email, String phone) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
