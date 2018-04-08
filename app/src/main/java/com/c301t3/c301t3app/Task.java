
package com.c301t3.c301t3app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import io.searchbox.annotations.JestId;

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
    private float price;
    private ArrayList<Bid> bids;
    private byte[] byteArrayImage;
    private double longitude;
    private double latitude;
    private String ownerName;
    @JestId
    private String id;
    private String owner;

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
        UserAccount u = ApplicationController.getCurrUser();
        if(u != null) {
            this.owner = u.getID();
            this.ownerName = u.getUsername();
        } else {
            this.owner = "NO_ONE";
        }
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
        } else if (name.length() > ApplicationController.MAX_TASK_NAME_LENGTH ) {
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
        } else if (description.length() > ApplicationController.MAX_TASK_DESC_LENGTH) {
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

    /**
     * Method that sets/edits name of Tasks.
     *
     * @param name: the name of the task.
     * @throws IllegalArgumentException: if the name is an empty string or if over 30 chars.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.length() == 0) {
            throw new IllegalArgumentException("Error: Name cannot be set with no characters");
        } else if (name.length() > ApplicationController.MAX_TASK_NAME_LENGTH ) {
            throw new IllegalArgumentException("Error: Name cannot go over 30 characters in length");
        }
        this.name = name;
    }

    /**
     * Method that sets/edits description of Tasks.
     *
     * @param description: the description of the task.
     * @throws IllegalArgumentException: if the name is an empty string or if over 300 chars.
     */
    public void setDescription(String description) throws IllegalArgumentException {
        if (description.length() == 0) {
            throw new IllegalArgumentException("Error: Description cannot be set with no characters");
        } else if (description.length() > ApplicationController.MAX_TASK_DESC_LENGTH) {
            throw new IllegalArgumentException("Error: Description cannot go over 300 characters in length");
        }
        this.description = description;
    }

    /**
     * Method that sets/edits description of Tasks.
     *
     * @param status: the status of the task.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Method that sets/edits longitude of Tasks.
     *
     * @param longitude: the longitude of the Task that's taking place.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Method that sets/edits latitude of Tasks.
     *
     * @param latitude: the latitude of the Task that's taking place.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Method that sets/edits picture of Tasks.
     *
     * @param picture: the picture of the Task.
     */
    public void setPicture(Bitmap picture) throws IllegalArgumentException {
        if (picture == null) {
            this.byteArrayImage = null;
            return;
        }

        picture = autoCompress(picture);
        int num_bytes = picture.getByteCount();

        if (num_bytes < 65536) {
            this.byteArrayImage = convertToByteArray(picture);
        } else {
            throw new IllegalArgumentException("Error: Picture cannot go over 65536 bytes");
        }

    }

    /**
     * Method that returns the name of Tasks.
     *
     * @return: name of the Task.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method that returns the description of Tasks.
     *
     * @return: description of the Task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Status that returns the description of Tasks.
     *
     * @return: status of the Task.
     */
    public TaskStatus getStatus() {
        return this.status;
    }

    /**
     * Method that returns the latitude of Task.
     *
     * @return: latitude of the Task
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Method that returns the longitude of Task.
     *
     * @return: longitude of the Task
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Method that reuturns the picture of Task.
     *
     * @return: picture of the Task
     */
    public Bitmap getPicture() {
        Bitmap bm = null;
        if (this.byteArrayImage != null && this.byteArrayImage.length > 0) {
             bm = convertToBitmap(this.byteArrayImage);
        }
        return bm;

    }

    /**
     * Override for the toString() Method to work with the adapter class of MyTasks.
     *
     * @return: message of summarizing the information of the Task.
     */
    @Override
    public String toString() {
        String message = this.name;
        for (int i = this.name.length() ; i < 31; i++) {
            message = message + "\t";
        }
        message = message + this.status;
        return message;
    }

    /**
     * Method that returns the price of Tasks.
     *
     * @return: price of the Task
     */
    public float getPrice() {
        return price;
    }

    /**
     * Method that sets/edits the price of Tasks.
     *
     * @param price: the new price of Task.
     * @throws IllegalArgumentException: if the new price is lower that 0.
     */
    public void setPrice(float price) throws IllegalArgumentException {
        if (price < 0) {
            throw new IllegalArgumentException("Error: Price cannot be lower than 0");
        } else {
            this.price = price;
        }
    }

    /**
     * Method that gets the list of bids of the Task.
     *
     * @return: ArrayList of bids associated to Task.
     */
    public ArrayList<Bid> getBids() { return this.bids; }

    /**
     * Method that overwrites the bids of the Task.
     *
     * @param bids: the ArrayList that represents all the bids.
     */
    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    /**
     * Method that adds the bid into the list of Bids of the Task.
     *
     * @param bid: the bid class that will be added on to Task's ArrayList.
     */
    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    /**
     * Method that removes the bid from the list of bids of the Task.
     *
     * @param bid: the bid that will be removed from the list of bids.
     */
    public void remBid(Bid bid) { this.bids.remove(bid); }

    /**
     * Method that removes the bids from the list of bids of the Task.
     *
     * @param i: the bid from the given index of the ArrayList that will be removed.
     */
    public void remBid(int i) { this.bids.remove(i); }
    
    /**
     * Setter for id
     * @param i: new id
     */
    public void setId(String i) {
        id = i;
    }

    /**
     * Getter for id
     * @return: String id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for owner
     * @return: String owner
     */
    public String getOwner() { return owner; }

    /**
     * Getter for owner's username
     * @return String ownerName
     */
    public String getOwnerName() {return ownerName;}

    /**
     * Setter for owner ID
     */
    public void setOwner(String id) {
        this.owner = id;
    }

    public void setOwnerName(String name) {
        this.ownerName = name;
  
    private byte[] convertToByteArray(Bitmap picture) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap convertToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    private Bitmap autoCompress(Bitmap picture) {
        Bitmap newPicture = picture;
        float span = Math.max(newPicture.getHeight(), newPicture.getWidth());

        while (true) {
            if (newPicture.getByteCount() > 65536) {
                span = (span / 4) * 3;
                newPicture = scaleDown(newPicture, span, true);
            } else if (span > 4096) {
                span = 4096;
                newPicture = scaleDown(newPicture, span, true);
            } else {
                break;
            }
        }

        return newPicture;
    }

    private static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                    boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

}

