package com.example.vulcanuniversity.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;

    private int courseId;

    @Ignore
    public NoteEntity() {
    }
    @Ignore
    public NoteEntity(int id, Date date, String text, int courseId) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.courseId = courseId;
    }

    public NoteEntity(Date date, String text, int courseId) {
        this.date = date;
        this.text = text;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text=" + text +
                ", courseId" + courseId +
                '}';
    }

    public int getTypeId() {
        return courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
