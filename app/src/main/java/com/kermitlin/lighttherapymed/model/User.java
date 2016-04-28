package com.kermitlin.lighttherapymed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

/**
 * Defines the data structure for User objects.
 */
public class User {
    private String name;
    private String email;
    private HashMap<String, Object> timestampEdit;
    private boolean hasAskNewPassword;

    /**
     * Required public constructor
     */
    public User() {
    }

    /**
     * Use this constructor to create new User.
     * Takes user name, email and timestampJoined as params
     *
     * @param name
     * @param email
     */
    public User(String name, String email, HashMap<String, Object> timestampJoined) {
        this.name = name;
        this.email = email;
        this.timestampEdit = timestampJoined;
        this.hasAskNewPassword = false;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimestampEdit() {
        return timestampEdit;
    }

    @JsonIgnore
    public long getTimestampEditLong() {
        return (long) timestampEdit.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    public boolean isHasAskNewPassword() {
        return hasAskNewPassword;
    }
}
