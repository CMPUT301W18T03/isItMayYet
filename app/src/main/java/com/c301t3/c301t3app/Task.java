package com.c301t3.c301t3app;

/**
 * Created by Henry on 2018-02-23.
 */

public class Task {

    String name;
    String description;
    String status;
    // Photo photo;     // This is commented out due to Photo not being implemented
    // GeoLoc location;     // This is commented out due to GeoLocation not being implemented

    Task() { // Task constructor
        this.name = "";
        this.description = "";
        this.status = "";
    }

    Task(String name) throws IllegalArgumentException {
        this();
        if (name.length() > 30 ) {
            throw new IllegalArgumentException("Error: Name cannot go over 30 characters in length");
        }
        this.name = name;
    }

    Task(String name, String description) throws IllegalArgumentException {
        this(name);
        if (description.length() > 300) {
            throw new IllegalArgumentException("Error: Description cannot go over 300 characters in length");
        }
        this.description = description;
    }

    Task(String name, String description, String status) {
        this(name, description);
        this.status = status;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.length() > 30 ) {
            throw new IllegalArgumentException("Error: Name cannot go over 30 characters in length");
        }
        this.name = name;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (description.length() > 300) {
            throw new IllegalArgumentException("Error: Description cannot go over 300 characters in length");
        }
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatus() {
        return this.status;
    }
}
