package com.kermitlin.lighttherapymed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

/**
 * Defines the data structure for both Active and Archived ShoppingList objects.
 */

public class TherapyList {
    private String listName;
    private HashMap<String, User> usersDeployed;
    private HashMap<String, Object> timestampEdit;
    private boolean switchOn;

    /**
     * Required public constructor
     */
    public TherapyList() {
    }


    public TherapyList(String listName, boolean switchOn, HashMap<String, Object> timestampEdit) {
        this.listName = listName;
        this.timestampEdit = timestampEdit;
        this.usersDeployed = new HashMap<>();
        this.switchOn = true;
    }

    public String getListName() {
        return listName;
    }

    public HashMap<String, Object> getTimestampEdit() {
        return timestampEdit;
    }

    @JsonIgnore
    public long getTimestampEditLong() {
        return (long) timestampEdit.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    public HashMap getUsersDeployed() {
        return usersDeployed;
    }

    public boolean isSwitchOn() {
        return switchOn;
    }
}


