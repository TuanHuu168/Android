package com.example.projectgk;

public class Unit implements ListItem {
    private String unitId;
    private String unitName;
    private String email;
    private String website;
    private String profilePicture;
    private String address;
    private String phoneNumber;
    private String parentUnitId;

    public Unit() {}

    public Unit(String unitId, String unitName, String email, String website, String profilePicture, String address, String phoneNumber, String parentUnitId) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.email = email;
        this.website = website;
        this.profilePicture = profilePicture;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.parentUnitId = parentUnitId;
    }

    // Getters
    public String getUnitId() {
        return unitId;
    }

    @Override
    public String getFullName() {
        return unitName;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String getProfilePicture() {
        return profilePicture;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getParentUnitId() {
        return parentUnitId;
    }

    // Setters
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setFullName(String unitName) {
        this.unitName = unitName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setParentUnitId(String parentUnitId) {
        this.parentUnitId = parentUnitId;
    }
}
