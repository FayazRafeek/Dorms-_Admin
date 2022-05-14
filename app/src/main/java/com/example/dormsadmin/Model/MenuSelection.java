package com.example.dormsadmin.Model;

import java.util.ArrayList;

public class MenuSelection {

    String selectionId;
    String studId, studentName, profileUrl;
    String adminId;
    String foodType;
    Long menuDate;
    Long selectionDate;

    ArrayList<FoodItem> foodItems;

    public MenuSelection() {
    }

    public MenuSelection(String selectionId, String studId, String studentName, String profileUrl, String adminId, String foodType, Long menuDate, Long selectionDate, ArrayList<FoodItem> foodItems) {
        this.selectionId = selectionId;
        this.studId = studId;
        this.studentName = studentName;
        this.profileUrl = profileUrl;
        this.adminId = adminId;
        this.foodType = foodType;
        this.menuDate = menuDate;
        this.selectionDate = selectionDate;
        this.foodItems = foodItems;
    }

    public String getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }


    public Long getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Long menuDate) {
        this.menuDate = menuDate;
    }

    public Long getSelectionDate() {
        return selectionDate;
    }

    public void setSelectionDate(Long selectionDate) {
        this.selectionDate = selectionDate;
    }

    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}


