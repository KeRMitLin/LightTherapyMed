package com.kermitlin.lighttherapymed.ui.operatingLists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyList;
import com.kermitlin.lighttherapymed.model.User;
import com.kermitlin.lighttherapymed.utils.Constants;

import java.util.HashMap;

public class OperatingListsFragment extends Fragment {
    private static final String LOG_TAG = OperatingListsFragment.class.getSimpleName();
    private OperatingListAdapter mOperatingListAdapter;
    private ListView mListView;
    private String mEncodedEmail;
    private ValueEventListener mSelectUserRefListener;
    private User mSelectUser;
    private Firebase selectUserRef;

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
        Firebase operatingListsRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS);

        //Fetch select user
        selectUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);

        mSelectUserRefListener = selectUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User selectUser = dataSnapshot.getValue(User.class);
                if (selectUser != null) mSelectUser = selectUser;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

        /**
         * Add ValueEventListeners to Firebase references
         * to control get data and control behavior and visibility of elements
         */
        mOperatingListAdapter = new OperatingListAdapter(getActivity(), TherapyList.class,
                R.layout.single_operating_list, operatingListsRef, mEncodedEmail);
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
                String listId = mOperatingListAdapter.getRef(position).getKey();
                ImageView iv = (ImageView) view.findViewById(R.id.image_view_deploy);

                Firebase usersDeployedRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS).
                        child(listId).child(Constants.FIREBASE_PROPERTY_USERS_DEPLOYED).
                        child(mEncodedEmail);

                Firebase deployedListsRef = new Firebase(Constants.FIREBASE_URL_DEPLOYED_LISTS).
                        child(mEncodedEmail).child(listId);

                //Not deployed
                if (iv.getVisibility() == View.INVISIBLE) {
                    usersDeployedRef.setValue(mSelectUser);

                    HashMap<String, Object> changedTimestampMap = new HashMap<>();
                    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP,
                            mOperatingListAdapter.getItem(position).getTimestampEditLong());

                    TherapyList selectTherapy = new TherapyList(mOperatingListAdapter.
                            getItem(position).getListName(), changedTimestampMap);

                    deployedListsRef.setValue(selectTherapy);

                    iv.setVisibility(View.VISIBLE);
                } else {
                    usersDeployedRef.removeValue();
                    deployedListsRef.removeValue();
                    iv.setVisibility(View.INVISIBLE);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOperatingListAdapter.cleanup();
        selectUserRef.removeEventListener(mSelectUserRefListener);
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}
