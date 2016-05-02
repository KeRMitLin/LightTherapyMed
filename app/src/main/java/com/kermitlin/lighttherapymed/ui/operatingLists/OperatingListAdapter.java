package com.kermitlin.lighttherapymed.ui.operatingLists;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.model.TherapyList;
import com.kermitlin.lighttherapymed.utils.Utils;

import java.util.Date;

public class OperatingListAdapter extends FirebaseListAdapter<TherapyList> {
    private String mEncodedEmail;

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    public OperatingListAdapter(Activity activity, Class<TherapyList> modelClass, int modelLayout,
                                Query ref, String encodedEmail) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
        this.mEncodedEmail = encodedEmail;
    }

    /**
     * Protected method that populates the view attached to the adapter (list_view_active_lists)
     * with items inflated from single_therapy_list.xml
     * populateView also handles data changes and updates the listView accordingly
     */
    @Override
    protected void populateView(View view, TherapyList therapyList, int i) {
        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewListName = (TextView) view.findViewById(R.id.text_view_list_name);
        TextView textViewEditTime = (TextView) view.findViewById(R.id.text_view_edit_time);
        ImageView imageViewDeploy = (ImageView) view.findViewById(R.id.image_view_deploy);

        /* Set the list name and owner */
        textViewListName.setText(therapyList.getListName());
        textViewEditTime.setText(String.valueOf(Utils.SIMPLE_DATE_FORMAT.format(
                new Date(therapyList.getTimestampEditLong()))));

        if (therapyList.getUsersDeployed() != null) {
            if (therapyList.getUsersDeployed().containsKey(mEncodedEmail)) {
                imageViewDeploy.setVisibility(View.VISIBLE);
            } else {
                imageViewDeploy.setVisibility(View.INVISIBLE);
            }
        }
    }
}
