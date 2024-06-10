package com.example.projectgk;

public class Employee implements ListItem{
    private String employeeId;
    private String fullName;
    private String position;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String unitId;

    // Constructor
    public Employee() {}

    public Employee(String employeeId, String fullName, String position, String email, String phoneNumber, String profilePicture, String unitId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.unitId = unitId;
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUnitId() {
        return unitId;
    }

    // Setters
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
