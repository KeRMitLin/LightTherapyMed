package com.kermitlin.lighttherapymed.ui.deployedLists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyList;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class DeployedListsFragment extends Fragment {
    private DeployedListAdapter mDeployedListAdapter;
    private ListView mListView;
    private String mEncodedEmail;

    public DeployedListsFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static DeployedListsFragment newInstance(String encodedEmail) {
        DeployedListsFragment fragment = new DeployedListsFragment();
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
        Firebase deployedListsRef = new Firebase(Constants.FIREBASE_URL_DEPLOYED_LISTS).child(mEncodedEmail);

        /**
         * Add ValueEventListeners to Firebase references
         * to control get data and control behavior and visibility of elements
         */
        mDeployedListAdapter = new DeployedListAdapter(getActivity(), TherapyList.class,
                R.layout.single_deployed_list, deployedListsRef);

        /**
         * Set the adapter to the mListView
         */
        mListView.setAdapter(mDeployedListAdapter);

        /**
         * Set interactive bits, such as click events and adapters
         */

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listId = mDeployedListAdapter.getRef(position).getKey();
                ImageView iv = (ImageView) view.findViewById(R.id.image_view_switch);

                Firebase deployedListRef = new Firebase(Constants.FIREBASE_URL_DEPLOYED_LISTS).
                        child(mEncodedEmail).child(listId);

                if (mDeployedListAdapter.getItem(position).isSwitchOn()) {
                    HashMap<String, Object> toggleIsSwitchOnMap = new HashMap<>();
                    toggleIsSwitchOnMap.put(Constants.FIREBASE_PROPERTY_SWITCH_ON, false);

                    deployedListRef.updateChildren(toggleIsSwitchOnMap);
                    iv.setImageResource(R.drawable.ic_lock);
                } else {
                    HashMap<String, Object> toggleIsSwitchOnMap = new HashMap<>();
                    toggleIsSwitchOnMap.put(Constants.FIREBASE_PROPERTY_SWITCH_ON, true);

                    deployedListRef.updateChildren(toggleIsSwitchOnMap);
                    iv.setImageResource(R.drawable.ic_unlock);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDeployedListAdapter.cleanup();
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}
