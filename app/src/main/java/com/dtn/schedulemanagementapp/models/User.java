package com.dtn.schedulemanagementapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.util.Date;

public class User implements Parcelable {
    private String username;
    private String password;
    private String fullName;
    private Date birthDate;
    private String email;
    private int role;

    public User() {

    }

    public User(String username, String password, String fullName, Date birthDate, String email, int role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.fullName +  " " + this.username + " role: " + this.role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(CalendarUtils.DateToString(birthDate, "dd/MM/yyyy"));
        dest.writeString(email);
        dest.writeString(String.valueOf(role));
    }

    private User(Parcel in) {
        username = in.readString();
        password = in.readString();
        fullName = in.readString();
        birthDate = CalendarUtils.StringToDate(in.readString(), "dd/MM/yyyy");
        email = in.readString();
        role = Integer.parseInt(in.readString());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
