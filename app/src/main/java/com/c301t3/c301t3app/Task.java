
package com.c301t3.c301t3app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Henry on 2018-02-23.
 */

public class Task implements Serializable {

    private String name;
    private String description;
    private TaskStatus status;
    private int price;
    private ArrayList<Bid> bids;

    // Photo photo;     // Commented out from not knowing how/what to represent photo with.
    // GeoLoc location;     // Commented out from not knowing how/what to represent location with.

    Task() { // Task constructor
        this.name = "";
        this.description = "";
        this.status = TaskStatus.REQUESTED;
    }

    Task(String name) throws IllegalArgumentException {
        this();
        if (name.length() == 0) {
            throw new IllegalArgumentException("Error: Name cannot be set with no characters");
        } else if (name.length() > 30 ) {
            throw new IllegalArgumentException("Error: Name cannot go over 30 characters in length");
        }
        this.name = name;
    }

    Task(String name, String description) throws IllegalArgumentException {
        this(name);
        if (description.length() == 0) {
            throw new IllegalArgumentException("Error: Description cannot be set with no characters");
        } else if (description.length() > 300) {
            throw new IllegalArgumentException("Error: Description cannot go over 300 characters in length");
        }
        this.description = description;
    }

    Task(String name, String description, TaskStatus status) {
        this(name, description);
        this.status = status;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.length() == 0) {
            throw new IllegalArgumentException("Error: Name cannot be set with no characters");
        } else if (name.length() > 30 ) {
            throw new IllegalArgumentException("Error: Name cannot go over 30 characters in length");
        }
        this.name = name;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (description.length() == 0) {
            throw new IllegalArgumentException("Error: Description cannot be set with no characters");
        } else if (description.length() > 300) {
            throw new IllegalArgumentException("Error: Description cannot go over 300 characters in length");
        }
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    @Override
    public String toString() {

        String message = this.name;
        for (int i = this.name.length() ; i < 31; i++) {
            message = message + "\t";
        }
        message = message + this.status;
        return message;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int p) {
        price = p;
    }

    public void addBid(Bid b) {
        bids.add(b);
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }
}

