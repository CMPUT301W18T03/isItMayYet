package com.c301t3.c301t3app;

/**
 * Created by jonah on 14/03/18.
 * Class for bids
 *
 * @author Jonah
 */

public class Bid {
    private float value;
    private String userId;

    /**
     * Creates a bid with a value and user
     *
     * @param v: value of bid
     * @param i: id of user placing bid
     */
    public Bid(float v, String i) {
        value = v;
        userId = i;
    }

    /**
     * Return the value of the bid
     *
     * @return: value of bid
     */
    public float getValue() {
        return value;
    }

    /**
     * Return the user id of the bidder
     *
     * @return: id of bidder
     */
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        String message = userId + "      " + String.valueOf(value);
        return message;
    }
}