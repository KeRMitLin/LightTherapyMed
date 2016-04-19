package com.kermitlin.lighttherapymed.model;

import java.util.HashMap;

/**
 * Defines the data structure for both Active and Archived ShoppingList objects.
 */

public class TherapyListContent {
    private String color, hz, time;

    /**
     * Required public constructor
     */
    public TherapyListContent() {
    }

    /**
     * Use this constructor to create new ShoppingLists.
     * Takes shopping list listName and owner. Set's the last
     * changed time to what is stored in ServerValue.TIMESTAMP
     *
     * @param color
     * @param hz
     * @param time
     */
    public TherapyListContent(String color, String hz, String time) {
        this.color = color;
        this.hz = hz;
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public String getHz() {
        return hz;
    }

    public String getTime() {
        return time;
    }
}


