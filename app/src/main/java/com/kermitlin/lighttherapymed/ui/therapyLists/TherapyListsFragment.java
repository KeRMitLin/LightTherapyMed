package com.kermitlin.lighttherapymed.ui.therapyLists;

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
import com.kermitlin.lighttherapymed.ui.therapyListContent.TherapyListContentActivity;
import com.kermitlin.lighttherapymed.utils.Constants;

public class TherapyListsFragment extends Fragment {
    private TherapyListAdapter mTherapyListAdapter;
    private ListView mListView;

    public TherapyListsFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static TherapyListsFragment newInstance() {
        TherapyListsFragment fragment = new TherapyListsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_therapy_lists, container, false);
        initializeScreen(rootView);

        /**
         * Create Firebase references
         */
        Firebase therapyListsRef = new Firebase(Constants.FIREBASE_URL_THERAPY_LISTS);

        /**
         * Add ValueEventListeners to Firebase references
         * to control get data and control behavior and visibility of elements
         */
        mTherapyListAdapter = new TherapyListAdapter(getActivity(), TherapyList.class,
                R.layout.single_therapy_list, therapyListsRef);

        /**
         * Set the adapter to the mListView
         */
        mListView.setAdapter(mTherapyListAdapter);

        /**
         * Set interactive bits, such as click events and adapters
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TherapyList selectedList = mTherapyListAdapter.getItem(position);
                if (selectedList != null) {
                    Intent intent = new Intent(getActivity(), TherapyListContentActivity.class);
                    /* Get the list ID using the adapter's get ref method to get the Firebase
                     * ref and then grab the key.
                     */
                    String listId = mTherapyListAdapter.getRef(position).getKey();
                    String listName = selectedList.getListName();
                    intent.putExtra(Constants.KEY_LIST_ID, listId);
                    intent.putExtra(Constants.KEY_LIST_NAME, listName);
                    /* Starts an active showing the details for the selected list */
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTherapyListAdapter.cleanup();
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}
