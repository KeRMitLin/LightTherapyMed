package com.kermitlin.lighttherapymed.ui.therapyListContent;

import android.app.Dialog;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyListContent;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

/**
 * Adds a new active pat list
 */
public class AddListContentDialogFragment extends EditListContentDialogFragment {
    private static final String LOG_TAG = TherapyListContent.class.getSimpleName();

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddListContentDialogFragment newInstance(String listId, String listName) {
        AddListContentDialogFragment addListContentDialogFragment = new AddListContentDialogFragment();
        Bundle bundle = EditListContentDialogFragment.newInstanceHelper(R.layout.dialog_add_list_content,
                listId, listName);
        bundle.putString(Constants.KEY_LIST_ID, listId);
        bundle.putString(Constants.KEY_LIST_NAME, listName);
        addListContentDialogFragment.setArguments(bundle);
        return addListContentDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        Dialog dialog = super.createDialogHelper(R.string.positive_button_add_item);

        return dialog;
    }

    /**
     * Add new active pat list
     */
    public void addTherapyListContent() {
        String userEnteredHz = mEditTextHz.getText().toString();
        String userEnteredTime = mEditTextTime.getText().toString();

        /**
         * If EditText input is not empty
         */
        if (!userEnteredHz.equals("") && !userEnteredTime.equals("")) {

            Firebase mInsertRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LIST_CONTENT).
                    child(mListId);
            Firebase newListRef = mInsertRef.push();

            String contentListId = newListRef.getKey();

            /* Build the shopping list */
            TherapyListContent newTherapyListContent = new TherapyListContent(mSelectColor, userEnteredHz, userEnteredTime);

            /* Add the shopping list */
            newListRef.setValue(newTherapyListContent);


            /**
             * Refresh listName and editTime in TherapyList
             */
            Firebase listsRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS).child(mListId);

            HashMap<String, Object> updatedProperties = new HashMap<String, Object>();

            /* Add the timestamp for last changed to the updatedProperties Hashmap */
            HashMap<String, Object> changedTimestampMap = new HashMap<>();
            changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

            /* Add the updated timestamp */
            updatedProperties.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_EDIT, changedTimestampMap);

            listsRef.updateChildren(updatedProperties);

            /* Close the dialog fragment */
            AddListContentDialogFragment.this.getDialog().cancel();
        }
    }
}
