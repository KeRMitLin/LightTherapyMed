package com.kermitlin.lighttherapymed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

/**
 * Defines the data structure for both Active and Archived ShoppingList objects.
 */

public class TherapyList {
    private String listName;
    private Boolean toggleSwitch;
    private HashMap<String, Object> timestampEdit;

    /**
     * Required public constructor
     */
    public TherapyList() {
    }

    /**
     * Use this constructor to create new ShoppingLists.
     * Takes shopping list listName and owner. Set's the last
     * changed time to what is stored in ServerValue.TIMESTAMP
     *
     * @param listName
     */
    public TherapyList(String listName, boolean toggleSwitch, HashMap<String, Object> timestampEdit) {
        this.listName = listName;
        this.toggleSwitch = toggleSwitch;
        this.timestampEdit = timestampEdit;
    }

    public String getListName() {
        return listName;
    }

    public boolean isToggleSwitch() {
        return toggleSwitch;
    }

    public HashMap<String, Object> getTimestampEdit() {
        return timestampEdit;
    }

    @JsonIgnore
    public long getTimestampEditLong() {
        return (long) timestampEdit.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}


