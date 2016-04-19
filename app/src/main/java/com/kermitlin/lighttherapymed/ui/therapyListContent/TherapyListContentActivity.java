package com.kermitlin.lighttherapymed.ui.therapyListContent;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    private String mListId;
    private Firebase mTherapyListRef;

    private TherapyList mTherapyList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_list_content);

        /* Get the push ID from the extra passed by ShoppingListFragment */
        Intent intent = this.getIntent();
        mListId = intent.getStringExtra(Constants.KEY_LIST_ID);
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

                /**
                 * Saving the most recent version of current shopping list into mShoppingList if present
                 * finish() the activity if the list is null (list was removed or unshared by it's owner
                 * while current user is in the list details activity)
                 */
                TherapyList therapyList = snapshot.getValue(TherapyList.class);

                if (therapyList == null) {
                    finish();
                    /**
                     * Make sure to call return, otherwise the rest of the method will execute,
                     * even after calling finish.
                     */
                    return;
                }
                mTherapyList = therapyList;
                /**
                 * Pass the shopping list to the adapter if it is not null.
                 * We do this here because mShoppingList is null when first created.
                 */
                mTherapyListContentAdapter.setTherapyList(mTherapyList);

                /* Calling invalidateOptionsMenu causes onCreateOptionsMenu to be called */
                invalidateOptionsMenu();

                /* Set title appropriately. */
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
//                if (view.getId() != R.id.list_view_footer_empty) {
//                    final ShoppingListItem selectedListItem = mActiveListItemAdapter.getItem(position);
//                    String itemId = mActiveListItemAdapter.getRef(position).getKey();
//
//                    if (selectedListItem != null) {
//                        /* If current user is shopping */
//                        if (mShopping) {
//
//                            /* Create map and fill it in with deep path multi write operations list */
//                            HashMap<String, Object> updatedItemBoughtData = new HashMap<String, Object>();
//
//                            /* Buy selected item if it is NOT already bought */
//                            if (!selectedListItem.isBought()) {
//                                updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT, true);
//                                updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT_BY, mEncodedEmail);
//                            } else {
//                                if (selectedListItem.getBoughtBy().equals(mEncodedEmail)) {
//                                    updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT, false);
//                                    updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT_BY, null);
//                                }
//                            }
//
//                            /* Do update */
//                            Firebase firebaseItemLocation = new Firebase(Constants.FIREBASE_URL_SHOPPING_LIST_ITEMS)
//                                    .child(mListId).child(itemId);
//                            firebaseItemLocation.updateChildren(updatedItemBoughtData, new Firebase.CompletionListener() {
//                                @Override
//                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                                    if (firebaseError != null) {
//                                        Log.d(LOG_TAG, getString(R.string.log_error_updating_data) +
//                                                firebaseError.getMessage());
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }
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

    /**
     * Remove current shopping list and its items from all nodes
     */
    public void removeList() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = RemoveListDialogFragment.newInstance(mTherapyList, mListId);
        dialog.show(getFragmentManager(), "RemoveListDialogFragment");
    }

    /**
     * Show the add list item dialog when user taps "Add list item" fab
     */
    public void showAddTherapyListContentDialog(View view) {

//        /* Create an instance of the dialog fragment and show it */
//        DialogFragment dialog = AddListItemDialogFragment.newInstance(mShoppingList, mListId, mEncodedEmail);
//        dialog.show(getFragmentManager(), "AddListItemDialogFragment");

        //insertTest
        Firebase mInsertRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LIST_CONTENT).
                child(mListId);
        Firebase newListRef = mInsertRef.push();

        /**
         * Set raw version of date to the ServerValue.TIMESTAMP value and save into
         * timestampCreatedMap
         */
        HashMap<String, Object> timestampCreated = new HashMap<>();

            /* Build the shopping list */
        TherapyListContent newTherapyListContent = new TherapyListContent("RED", "3", "10");

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
    }

    /**
     * Show edit list name dialog when user selects "Edit list name" menu item
     */
    public void showEditListNameDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListNameDialogFragment.newInstance(mTherapyList, mListId);
        dialog.show(this.getFragmentManager(), "EditListNameDialogFragment");
    }

}