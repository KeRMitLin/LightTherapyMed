package com.kermitlin.lighttherapymed.ui.usersLists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.User;
import com.kermitlin.lighttherapymed.utils.Constants;

public class UsersListsFragment extends Fragment {
    private UsersListAdapter mUsersListAdapter;
    private ListView mListView;

    public UsersListsFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static UsersListsFragment newInstance() {
        UsersListsFragment fragment = new UsersListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_deployed_lists, container, false);
        initializeScreen(rootView);

        /**
         * Create Firebase references
         */
        Firebase usersListsRef = new Firebase(Constants.FIREBASE_URL_USERS);

        /**
         * Add ValueEventListeners to Firebase references
         * to control get data and control behavior and visibility of elements
         */
        mUsersListAdapter = new UsersListAdapter(getActivity(), User.class,
                R.layout.single_users_list, usersListsRef);

        /**
         * Set the adapter to the mListView
         */
        mListView.setAdapter(mUsersListAdapter);

        /**
         * Set interactive bits, such as click events and adapters
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ShoppingList selectedList = mActiveListAdapter.getItem(position);
//                if (selectedList != null) {
//                    Intent intent = new Intent(getActivity(), ActiveListDetailsActivity.class);
//                    /* Get the list ID using the adapter's get ref method to get the Firebase
//                     * ref and then grab the key.
//                     */
//                    String listId = mActiveListAdapter.getRef(position).getKey();
//                    intent.putExtra(Constants.KEY_LIST_ID, listId);
//                    /* Starts an active showing the details for the selected list */
//                    startActivity(intent);
//                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUsersListAdapter.cleanup();
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}
