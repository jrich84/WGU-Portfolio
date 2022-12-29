package com.example.vulcanuniversity.database;

import android.icu.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "Assessments")
public class AssessmentEntity {


    @PrimaryKey(autoGenerate = true)
    @NonNull                            public int assessmentId;
    @ColumnInfo(name = "title")         public String title;
    @ColumnInfo(name = "dueDate")       public Date dueDate;
    @ColumnInfo(name = "type")          public String type;
    @ColumnInfo(name = "alertsEnabled") public boolean alertsEnabled;
    @ColumnInfo(name = "courseId")      public int courseId;
    /**
     * Create a new assessment
     * @param title     a title for the assessment
     * @param type      the type of assessment
     * @param dueDate      the date the assessment is due
     */
    @Ignore
    public AssessmentEntity(String title, String type, Date dueDate) {
        this.title = title;
        if(dueDate == null) {
            Calendar c = Calendar.getInstance();
            this.dueDate = c.getTime();
        } else {
            this.dueDate = dueDate;
        }
        this.type = type;
    }
    public AssessmentEntity(int assessmentId, String title, String type, Boolean alertsEnabled,Date dueDate, int courseId) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.type = type;
        this.alertsEnabled  = alertsEnabled;
        this.dueDate = dueDate;
        this.courseId = courseId;
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course) {
        this.courseId = course;
    }

    public int getId() {
        return assessmentId;
    }

    public void setId(int id) {
        this.assessmentId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date date) {
        this.dueDate = date;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getAlertsEnabled() {
        return alertsEnabled;
    }

    public void setAlertsEnabled(boolean alertsEnabled) {
        this.alertsEnabled = alertsEnabled;
    }

    @Override
    public String toString() {

        return "AssessmentEntity{" +
                "assessmentId=" + assessmentId +
                ", title=" + title +
                ", dueDate=" + dueDate +
                ", type=" + type;
    }
}
