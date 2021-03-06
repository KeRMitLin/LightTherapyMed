package com.kermitlin.lighttherapymed.utils;

import com.kermitlin.lighttherapymed.BuildConfig;

/**
 * Constants class store most important strings and paths of the app
 */
public final class Constants {

    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where active lists are stored (ie "activeLists")
     */
    public static final String FIREBASE_LOCATION_DEPLOYED_LISTS = "deployedLists";
    public static final String FIREBASE_LOCATION_THERAPY_LIST_CONTENT = "therapyListContent";
    public static final String FIREBASE_LOCATION_THERAPY_LISTS = "therapyLists";
    public static final String FIREBASE_LOCATION_USERS = "users";


    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_BOUGHT = "bought";
    public static final String FIREBASE_PROPERTY_BOUGHT_BY = "boughtBy";
    public static final String FIREBASE_PROPERTY_LIST_NAME = "listName";
    public static final String FIREBASE_PROPERTY_TIMESTAMP_EDIT = "timestampEdit";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_PROPERTY_USERS_DEPLOYED = "usersDeployed";
    public static final String FIREBASE_PROPERTY_SWITCH_ON = "switchOn";
    public static final String FIREBASE_PROPERTY_EMAIL = "email";
    public static final String FIREBASE_PROPERTY_USER_HAS_VERIFIED_MAIL = "hasVerifiedMail";

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_DEPLOYED_LISTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_DEPLOYED_LISTS;
    public static final String FIREBASE_URL_THERAPY_LIST_CONTENT = FIREBASE_URL + "/" + FIREBASE_LOCATION_THERAPY_LIST_CONTENT;
    public static final String FIREBASE_URL_THERAPY_LISTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_THERAPY_LISTS;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;


    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_LIST_NAME = "LIST_NAME";
    public static final String KEY_LAYOUT_RESOURCE = "LAYOUT_RESOURCE";
    public static final String KEY_LIST_ID = "LIST_ID";
    public static final String KEY_LIST_ITEM_NAME = "ITEM_NAME";
    public static final String KEY_LIST_ITEM_ID = "LIST_ITEM_ID";
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";
    public static final String KEY_USER = "USER";
    public static final String KEY_LIST_OWNER = "LIST_OWNER";
    public static final String KEY_SIGNUP_EMAIL = "SIGNUP_EMAIL";


    /**
     * Constants for Firebase login
     */
    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";
}
