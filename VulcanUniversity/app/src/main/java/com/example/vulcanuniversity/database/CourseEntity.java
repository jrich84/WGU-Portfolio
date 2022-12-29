package com.example.vulcanuniversity.database;

import android.icu.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * A course that the student is enrolled in
 */

@Entity(tableName = "courses")
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull public int courseId;
    @ColumnInfo(name = "title") public String title;
    @ColumnInfo(name = "startDate") public Date startDate;
    @ColumnInfo(name = "endDate") public Date endDate;
    @ColumnInfo(name = "status") public String status;
    @ColumnInfo(name = "alertsEnabled") public boolean alertsEnabled;
    @ColumnInfo(name = "termId") public int termId;
    @Ignore public static int length = 6;

    /**
     * Create a new course
     */
    @Ignore
    public CourseEntity(String title, String status, Date startDate, Date endDate) {
        this.title = title;
        this.status = status;

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MONTH, length);
        c.add(Calendar.DATE, -1);
        endDate = c.getTime();
    }
    public CourseEntity(int courseId, String title, String status, Date startDate, Date endDate, boolean alertsEnabled, int termId) {
        this.courseId = courseId;
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.alertsEnabled = alertsEnabled;
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int id) {
        this.courseId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getAlertsEnabled() {
        return alertsEnabled;
    }

    public void setAlertsEnabled(boolean alertsEnabled) {
        this.alertsEnabled = alertsEnabled;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStart(Date date) {
        this.startDate = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String type) {
        this.status = type;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    public String toString() {

        return "TermEntity{" +
                "courseId=" + courseId +
                "title=" + title +
                "status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", termId=" + termId ;
    }
}