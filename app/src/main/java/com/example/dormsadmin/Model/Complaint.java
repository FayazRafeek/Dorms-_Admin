package com.example.dormsadmin.Model;

public class Complaint {

    String complaintId;
    String userId;
    String adminId;
    String complaint;
    String status;
    String timestamp;
    String studName, profileUrl, college;

    String reply;

    public Complaint(String complaintId, String userId, String adminId, String complaint, String status, String timestamp) {
        this.complaintId = complaintId;
        this.userId = userId;
        this.adminId = adminId;
        this.complaint = complaint;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Complaint(String complaintId, String userId, String adminId, String complaint, String status, String timestamp, String studName, String profileUrl, String college) {
        this.complaintId = complaintId;
        this.userId = userId;
        this.adminId = adminId;
        this.complaint = complaint;
        this.status = status;
        this.timestamp = timestamp;
        this.studName = studName;
        this.profileUrl = profileUrl;
        this.college = college;
    }

    public Complaint() {
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
