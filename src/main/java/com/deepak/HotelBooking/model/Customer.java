package com.deepak.HotelBooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer extends BaseEntity {
    private String fullName;
    @Column(unique=true)
    private String emailId;
    @Column(unique=true)
    private String phoneNumber;
    @Column(unique=true)
    private String panNumber;

    public Customer() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
}
