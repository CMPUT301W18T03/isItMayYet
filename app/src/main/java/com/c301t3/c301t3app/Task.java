
package com.c301t3.c301t3app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Henry on 2018-02-23.
 */

/**
 * Created by Henry on 23/02/18.
 * Class for that represents a Task, which contains
 * data for its name, description, status, price,
 * and bids from others.
 *
 * @author Henry
 * @version 3.0
 */
public class Task implements Serializable {

    private String name;
    private String description;
    private TaskStatus status;
    private int price;
    private ArrayList<Bid> bids;

    // Photo photo;     // Commented out from not knowing how/what to represent photo with.
    // GeoLoc location;     // Commented out from not knowing how/what to represent location with.

    /**
     * Constructor for Task if no arguments are given.
     */
    Task() {
        this.name = "";
        this.description = "";
        this.status = TaskStatus.REQUESTED;
        this.price = 0;
        this.bids = new ArrayList<Bid>();
    }

    /**
     * Constructor for Task if the name argument is used.
     *
     * @param name: the name of the task.
     */
    Task(String name) throws IllegalArgumentException {
        this();
        if (name.length() == 0) {
            throw new IllegalArgumentException("Error: Name cannot be set with no characters");
        } else if (name.length() > 30 ) {
            throw new IllegalArgumentException("Error: Name cannot go over 30 characters in length");
        }
        this.name = name;
    }

    /**
     * Constructor for Task if the name and
     * description argument is used.
     *
     * @param name: the name of the task.
     * @param description: the description of the task.
     */
    Task(String name, String description) throws IllegalArgumentException {
        this(name);
        if (description.length() == 0) {
            throw new IllegalArgumentException("Error: Description cannot be set with no characters");
        } else if (description.length() > 300) {
            throw new IllegalArgumentException("Error: Description cannot go over 300 characters in length");
        }
        this.description = description;
    }

    /**
     * Constructor for Task if the name, description,
     * and status argument is used.
     *
     * @param name: the name of the task.
     * @param description: the description of the task.
     * @param status: the status of the task.
     */
    Task(String name, String description, TaskStatus status) {
        this(name, description);
        this.status = status;
    }

    /**
     * Constructor for Task if the name, description,
     * status, and price argument is used.
     *
     * @param name: the name of the task.
     * @param description: the descriptio of the task.
     * @param status: the status of the tasks.
     * @param price: the price of the tasks.
     */
    Task(String name, String description, TaskStatus status, int price) {
        this(name, description, status);
        this.price = price;
    }

    /**
     * Constructor for Task if the name, description,
     * status, price, and bids argument is used.
     *
     * @param name: the name of the task.
     * @param description: the descriptio of the task.
     * @param status: the status of the tasks.
     * @param price: the price of the tasks.
     * @param bids: the list of bids that the task has.
     */
    Task(String name, String description, TaskStatus status, int price, ArrayList<Bid> bids) {
        this(name, description, status, price);
        this.bids = bids;
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

    public void setPrice(int price) throws IllegalArgumentException {
        if (price < 0) {
            throw new IllegalArgumentException("Error: Price cannot be lower than 0");
        } else {
            this.price = price;
        }
    }

    public ArrayList<Bid> getBids() { return this.bids; }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }
}

