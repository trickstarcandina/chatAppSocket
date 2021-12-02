package com.example.chattogether.data.old;

public class User {
    private String id;
    private String username;
    private String imageUrl;
    private String email;
    private String fullname;
    private String location;
    private String live_in;
    private String education;
    private String relationship;
    private String password;
    private String background;
    private String online;
    private int avatarId;
    private int backgroundId;

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public User(String id, String username, String imageUrl, String email, String fullname, String location, String live_in, String education, String relationship, String password, String background, String online, int avatarId, int backgroundId) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.email = email;
        this.fullname = fullname;
        this.location = location;
        this.live_in = live_in;
        this.education = education;
        this.relationship = relationship;
        this.password = password;
        this.background = background;
        this.online = online;
        this.avatarId = avatarId;
        this.backgroundId = backgroundId;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public User(String id, String username, String imageUrl, String email, String fullname, String location, String live_in, String education, String relationship, String password, String background, String online) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.email = email;
        this.fullname = fullname;
        this.location = location;
        this.live_in = live_in;
        this.education = education;
        this.relationship = relationship;
        this.password = password;
        this.background = background;
        this.online = online;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String id, String username, String imageUrl, String email, String fullname, String location, String live_in, String education, String relationship, String password, String bgUrl) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.email = email;
        this.fullname = fullname;
        this.location = location;
        this.live_in = live_in;
        this.education = education;
        this.relationship = relationship;
        this.password = password;
        this.background = bgUrl;
    }

    public String getLive_in() {
        return live_in;
    }

    public void setLive_in(String live_in) {
        this.live_in = live_in;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User(String id, String username, String imageUrl, String email) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
