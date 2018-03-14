package com.c301t3.c301t3app;

/**
 * enum for task status
 *
 * @author jonah
 */

public enum TaskStatus {
    /**
     * Requested (no bids placed)
     */
    REQUESTED,

    /**
     * Bidded (bids placed, none accepted)
     */
    BIDDED,

    /**
     * Assigned (bid accepted)
     */
    ASSIGNED,

    /**
     * Completed (task finished)
     */
    COMPLETED
}
