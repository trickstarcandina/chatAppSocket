package com.example.chattogether.data.old;

public class User {
    private boolean emailIsVerified;
    private boolean phoneIsVerified;
    private double coin;
    private String username;
    private String idString;
    private String displayName;
    private String birthday;
    private String avatar;
    private String email;
    private String phone;
    private String deviceID;
    private String idCard;
    private String region;
    private String address;
    private String postalCode;
    private String country;

    public User(boolean emailIsVerified, boolean phoneIsVerified, double coin, String username, String idString, String displayName, String birthday, String avatar, String email, String phone, String deviceID, String idCard, String region, String address, String postalCode, String country) {
        this.emailIsVerified = emailIsVerified;
        this.phoneIsVerified = phoneIsVerified;
        this.coin = coin;
        this.username = username;
        this.idString = idString;
        this.displayName = displayName;
        this.birthday = birthday;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.deviceID = deviceID;
        this.idCard = idCard;
        this.region = region;
        this.address = address;
        this.postalCode = postalCode;
        this.country = country;
    }

    public boolean isEmailIsVerified() {
        return emailIsVerified;
    }

    public void setEmailIsVerified(boolean emailIsVerified) {
        this.emailIsVerified = emailIsVerified;
    }

    public boolean isPhoneIsVerified() {
        return phoneIsVerified;
    }

    public void setPhoneIsVerified(boolean phoneIsVerified) {
        this.phoneIsVerified = phoneIsVerified;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
