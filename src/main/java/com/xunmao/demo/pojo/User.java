package com.xunmao.demo.pojo;

import java.util.Date;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private Date lastUpdate;

    public User(int userId, String firstName, String lastName, Date lastUpdate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate=" + lastUpdate + ", userId="
                + userId + "]";
    }
}
