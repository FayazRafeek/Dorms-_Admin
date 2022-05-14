package com.example.dormsadmin.Model;

public class Leave {

    String leaveId;
    String studId,adminId;
    String studentName;
    String fromDate, toDate;
    String reason;
    String status;
    String response;

    public Leave() {
    }

    public Leave(String leaveId, String studId, String adminId, String studentName, String fromDate, String toDate, String reason, String status, String response) {
        this.leaveId = leaveId;
        this.studId = studId;
        this.adminId = adminId;
        this.studentName = studentName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.response = response;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
