package com.example.vulcanuniversity.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentors")
public class MentorEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull    public int mentorId;
    @ColumnInfo(name = "name")   public String name;
    @ColumnInfo(name = "phoneNumber") public String phoneNumber;
    @ColumnInfo(name = "email")  public String email;
    @ColumnInfo(name = "courseId")  public int courseId;

    @Ignore
    public MentorEntity(String name,String phoneNumber,String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public MentorEntity(int mentorId,String name,String phoneNumber, String email, int courseId) {
        this.mentorId = mentorId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.courseId = courseId;
    }

    @Override
    public String toString() {

        return "MentorEntity{" +
                "mentorId=" + mentorId +
                ", name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", courseId=" + courseId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
