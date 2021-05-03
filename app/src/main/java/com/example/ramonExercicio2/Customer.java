package com.example.ramonExercicio2;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;


@RequiresApi(api = Build.VERSION_CODES.O)
public class Customer implements Serializable {

    private Long id;

    private String name;

    private long birthDateInMillis;

    private String phoneNumber;

    private boolean blacklist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthDateInMillis() {
        return birthDateInMillis;
    }

    public void setBirthDateInMillis(long birthDateInMillis) {
        this.birthDateInMillis = birthDateInMillis;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDateInMillis=" + birthDateInMillis +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", blacklist=" + blacklist +
                '}';
    }
}