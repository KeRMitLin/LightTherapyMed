package com.kermitlin.lighttherapymed.ui.therapyListContent;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyList;
import com.kermitlin.lighttherapymed.model.TherapyListContent;
import com.kermitlin.lighttherapymed.ui.therapyLists.AddTherapyListDialogFragment;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

/**
 * Adds a new active pat list
 */
public class AddListContentDialogFragment extends AddListItemDialogFragment {
    private static final String LOG_TAG = TherapyListContent.class.getSimpleName();
    private EditText mEditTextListName;
    private String listID;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddListContentDialogFragment newInstance(TherapyList therapyList,String listId) {
        AddListContentDialogFragment addListContentDialogFragment = new AddListContentDialogFragment();
        Bundle bundle = AddListItemDialogFragment.newInstanceHelper(therapyList,
                R.layout.dialog_add_list_content, listId);
        bundle.putString(Constants.KEY_LIST_ID, listId);
        addListContentDialogFragment.setArguments(bundle);
        return addListContentDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listID = getArguments().getString(Constants.KEY_LIST_ID);
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
        String userSelectColor = mSelectColor;
        String userEnteredHz = mEditTextHz.getText().toString();
        String userEnteredTime = mEditTextTime.getText().toString();

        /**
         * If EditText input is not empty
         */
        if (!userEnteredHz.equals("") && !userEnteredTime.equals("")) {

            Firebase mInsertRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LIST_CONTENT).
                    child(listID);
            Firebase newListRef = mInsertRef.push();

            /**
             * Set raw version of date to the ServerValue.TIMESTAMP value and save into
             * timestampCreatedMap
             */
            HashMap<String, Object> timestampCreated = new HashMap<>();

            /* Build the shopping list */
            TherapyListContent newTherapyListContent = new TherapyListContent(mSelectColor, userEnteredHz, userEnteredTime);

            /* Add the shopping list */
            newListRef.setValue(newTherapyListContent);

            /**
             * Refresh editTime in TherapyList
             */
            Firebase listsRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS).child(mListId).
                    child(Constants.FIREBASE_PROPERTY_TIMESTAMP_EDIT);
            HashMap<String, Object> timestampEdit = new HashMap<String, Object>();
            timestampEdit.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
            listsRef.updateChildren(timestampEdit);

            /* Close the dialog fragment */
            AddListContentDialogFragment.this.getDialog().cancel();
        }
    }
}
