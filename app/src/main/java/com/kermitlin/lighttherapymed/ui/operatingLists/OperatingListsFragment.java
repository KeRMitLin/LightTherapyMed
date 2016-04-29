package com.kermitlin.lighttherapymed.ui.operatingLists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyList;
import com.kermitlin.lighttherapymed.ui.DeployedActivity;
import com.kermitlin.lighttherapymed.utils.Constants;

public class OperatingListsFragment extends Fragment {
    private OperatingListAdapter mOperatingListAdapter;
    private ListView mListView;
    private String mEncodedEmail;

    public OperatingListsFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static OperatingListsFragment newInstance(String encodedEmail) {
        OperatingListsFragment fragment = new OperatingListsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_ENCODED_EMAIL, encodedEmail);
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
            mEncodedEmail = getArguments().getString(Constants.KEY_ENCODED_EMAIL);
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
        mOperatingListAdapter = new OperatingListAdapter(getActivity(), TherapyList.class,
                R.layout.single_users_list, usersListsRef);

        /**
         * Set the adapter to the mListView
         */
        mListView.setAdapter(mOperatingListAdapter);

        /**
         * Set interactive bits, such as click events and adapters
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                User selectedList = mUsersListAdapter.getItem(position);
//                if (selectedList != null) {
//                    Intent intent = new Intent(getActivity(), DeployedActivity.class);
//                    /* Get the list ID using the adapter's get ref method to get the Firebase
//                     * ref and then grab the key.
//                     */
//                    String encodedEmail = mUsersListAdapter.getRef(position).getKey();
//                    intent.putExtra(Constants.KEY_ENCODED_EMAIL, encodedEmail);
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
        mOperatingListAdapter.cleanup();
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}
