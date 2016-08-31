package com.kermitlin.lighttherapymed.ui.therapyListContent;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyList;
import com.kermitlin.lighttherapymed.model.TherapyListContent;
import com.kermitlin.lighttherapymed.ui.BaseActivity;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

public class TherapyListContentActivity extends BaseActivity {
    private static final String LOG_TAG = TherapyListContentActivity.class.getSimpleName();
    private ValueEventListener mTherapyListRefListener;
    private TherapyListContentAdapter mTherapyListContentAdapter;
    private ListView mListView;
    private String mListId, mListName;
    private Firebase mTherapyListRef;
    private TherapyList therapyList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_list_content);

        /* Get the push ID from the extra passed by ShoppingListFragment */
        Intent intent = this.getIntent();
        mListId = intent.getStringExtra(Constants.KEY_LIST_ID);
        mListName = intent.getStringExtra(Constants.KEY_LIST_NAME);
        if (mListId == null) {
            /* No point in continuing without a valid ID. */
            finish();
            return;
        }

        /**
         * Create Firebase references
         */
        mTherapyListRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS).child(mListId);
        Firebase listContentRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LIST_CONTENT).child(mListId);

        /**
         * Link layout elements from XML and setup the toolbar
         */
        initializeScreen();

        /**
         * Setup the adapter
         */
        mTherapyListContentAdapter = new TherapyListContentAdapter(this, TherapyListContent.class,
                R.layout.single_therapy_list_content, listContentRef, mListId);
        /* Create ActiveListItemAdapter and set to listView */
        mListView.setAdapter(mTherapyListContentAdapter);

        /**
         * Save the most recent version of current shopping list into mShoppingList instance
         * variable an update the UI to match the current list.
         */
        mTherapyListRefListener = mTherapyListRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                therapyList = snapshot.getValue(TherapyList.class);

                if (therapyList == null) {
                    finish();

                    return;
                }

                invalidateOptionsMenu();

                setTitle(therapyList.getListName());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

        /**
         * Set up click listeners for interaction.
         */

        /* Perform buy/return action on listView item click event if current user is shopping. */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /* Check that the view is not the empty footer item */
                if (view.getId() != R.id.list_view_footer_empty) {
                    final TherapyListContent selectedListItem = mTherapyListContentAdapter.getItem(position);
                    String itemId = mTherapyListContentAdapter.getRef(position).getKey();

                    if (selectedListItem != null) {

                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_list_details, menu);

        /**
         * Get menu items
         */
        MenuItem remove = menu.findItem(R.id.action_remove_list);
        MenuItem edit = menu.findItem(R.id.action_edit_list_name);

        /* Only the edit and remove options are implemented */
        remove.setVisible(true);
        edit.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /**
         * Show edit list dialog when the edit action is selected
         */
        if (id == R.id.action_edit_list_name) {
            showEditListNameDialog();
            return true;
        }

        /**
         * removeList() when the remove action is selected
         */
        if (id == R.id.action_remove_list) {
            removeList();
            return true;
        }
        if (id == android.R.id.home) {

            refreshListName();

            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Cleanup when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mTherapyListContentAdapter.cleanup();
        mTherapyListRef.removeEventListener(mTherapyListRefListener);
    }

    /**
     * Link layout elements from XML and setup the toolbar
     */
    private void initializeScreen() {
        mListView = (ListView) findViewById(R.id.list_view_shopping_list_items);

        /* Inflate the footer, set root layout to null*/
        View footer = getLayoutInflater().inflate(R.layout.footer_empty, null);
        mListView.addFooterView(footer);
    }

    public void refreshListName() {

        if (!mTherapyListContentAdapter.isEmpty()) {
            String originTitle, newTitle, nickColor;
            double totalTime = 0.0, firstHz, secondHz = 0.0;
            int phaseNum = 0;

            originTitle = therapyList.getListName();
            if (originTitle.contains("-")) {
                String[] tempTitle = originTitle.split(" - ");
                originTitle = tempTitle[0];
            }

            nickColor = mTherapyListContentAdapter.getItem(0).getColor();
            phaseNum = mTherapyListContentAdapter.getCount();

            for (int i = 0; i < phaseNum; i = i + 1) {
                totalTime = totalTime + Double.parseDouble(mTherapyListContentAdapter.getItem(i).
                        getTime());
            }

            firstHz = Double.parseDouble(mTherapyListContentAdapter.getItem(0).getHz());
            if (phaseNum > 1) {
                secondHz = Double.parseDouble(mTherapyListContentAdapter.getItem(1).getHz());
            }

            if (secondHz == 0.0) {
                newTitle = originTitle + " - " + nickColor + " - " + phaseNum + " - " + totalTime +
                        " - " + firstHz;
            } else {
                newTitle = originTitle + " - " + nickColor + " - " + phaseNum + " - " + totalTime +
                        " - " + firstHz + " - " + secondHz;
            }

            //Input to Firebase
            Firebase therapyListRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS).
                    child(mListId);

                    /* Make a Hashmap for the specific properties you are changing */
            HashMap<String, Object> updatedProperties = new HashMap<String, Object>();
            updatedProperties.put(Constants.FIREBASE_PROPERTY_LIST_NAME, newTitle);

                    /* Add the timestamp for last changed to the updatedProperties Hashmap */
            HashMap<String, Object> changedTimestampMap = new HashMap<>();
            changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    /* Add the updated timestamp */
            updatedProperties.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_EDIT, changedTimestampMap);

                    /* Do the update */
            therapyListRef.updateChildren(updatedProperties);
        }
    }

    /**
     * Remove current shopping list and its items from all nodes
     */
    public void removeList() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = RemoveListDialogFragment.newInstance(mListId);
        dialog.show(getFragmentManager(), "RemoveListDialogFragment");
    }

    /**
     * Show the add list item dialog when user taps "Add list item" fab
     */
    public void showAddTherapyListContentDialog(View view) {

        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddListContentDialogFragment.newInstance(mListId, mListName);
        dialog.show(getFragmentManager(), "AddListContentDialogFragment");
    }

    /**
     * Show edit list name dialog when user selects "Edit list name" menu item
     */
    public void showEditListNameDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListNameDialogFragment.newInstance(therapyList, mListId);
        dialog.show(this.getFragmentManager(), "EditListNameDialogFragment");
    }

}
