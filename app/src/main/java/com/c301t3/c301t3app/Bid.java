package com.c301t3.c301t3app;

/**
 * Created by jonah on 14/03/18.
 * Class for bids
 *
 * @author Jonah
 */

public class Bid {
    private int value;
    private int userId;

    /**
     * Creates a bid with a value and user
     *
     * @param v: value of bid in cents
     * @param i: id of user placing bid
     */
    public Bid(int v, int i) {
        value = v;
        userId = i;
    }

    /**
     * Return the value of the bid
     *
     * @return: value of bid in cents
     */
    public int getValue() {
        return value;
    }

    /**
     * Return the user id of the bidder
     *
     * @return: id of bidder
     */
    public int getUserId() {
        return userId;
    }
}