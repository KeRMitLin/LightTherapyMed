package com.kermitlin.lighttherapymed.ui.therapyListContent;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.utils.Constants;

/**
 * Base class for {@link DialogFragment}s involved with editing a shopping list.
 */
public abstract class EditListContentDialogFragment extends DialogFragment {
    String mListId;
    Button bt_red, bt_orange, bt_yellow, bt_green, bt_blue, bt_purple, bt_black, bt_white, bt_random;
    ImageView check_red, check_orange, check_yellow, check_green, check_blue, check_purple, check_black,
            check_white, check_random;
    EditText mEditTextHz, mEditTextTime;
    String mListName, mSelectColor;
    int mResource;

    /**
     * Helper method that creates a basic bundle of all of the information needed to change
     * values in a shopping list.
     *
     * @param resource
     * @return
     */
    protected static Bundle newInstanceHelper(int resource, String listId, String listName) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_LIST_ID, listId);
        bundle.putString(Constants.KEY_LIST_NAME, listName);
        bundle.putInt(Constants.KEY_LAYOUT_RESOURCE, resource);
        return bundle;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListId = getArguments().getString(Constants.KEY_LIST_ID);
        mListName = getArguments().getString(Constants.KEY_LIST_NAME);
        mResource = getArguments().getInt(Constants.KEY_LAYOUT_RESOURCE);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    protected Dialog createDialogHelper(int stringResourceForPositiveButton) {
        /* Use the Builder class for convenient dialog construction */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        /* Inflate the layout, set root ViewGroup to null*/
        View rootView = inflater.inflate(mResource, null);
        bt_red = (Button) rootView.findViewById(R.id.bt_red);
        bt_orange = (Button) rootView.findViewById(R.id.bt_orange);
        bt_yellow = (Button) rootView.findViewById(R.id.bt_yellow);
        bt_green = (Button) rootView.findViewById(R.id.bt_green);
        bt_blue = (Button) rootView.findViewById(R.id.bt_blue);
        bt_purple = (Button) rootView.findViewById(R.id.bt_purple);
        bt_black = (Button) rootView.findViewById(R.id.bt_black);
        bt_white = (Button) rootView.findViewById(R.id.bt_white);
        bt_random = (Button) rootView.findViewById(R.id.bt_random);

        check_red = (ImageView) rootView.findViewById(R.id.check_red);
        check_orange = (ImageView) rootView.findViewById(R.id.check_orange);
        check_yellow = (ImageView) rootView.findViewById(R.id.check_yellow);
        check_green = (ImageView) rootView.findViewById(R.id.check_green);
        check_blue = (ImageView) rootView.findViewById(R.id.check_blue);
        check_purple = (ImageView) rootView.findViewById(R.id.check_purple);
        check_black = (ImageView) rootView.findViewById(R.id.check_black);
        check_white = (ImageView) rootView.findViewById(R.id.check_white);
        check_random = (ImageView) rootView.findViewById(R.id.check_random);

        mEditTextHz = (EditText) rootView.findViewById(R.id.edit_text_hz);
        mEditTextTime = (EditText) rootView.findViewById(R.id.edit_text_time);

        bt_red.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "紅色";

                check_red.setVisibility(View.VISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_orange.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "橘色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.VISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_yellow.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "黃色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.VISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_green.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "綠色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.VISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_blue.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "藍色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.VISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_purple.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "紫色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.VISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_black.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "黑色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.VISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_white.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "白色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.VISIBLE);
                check_random.setVisibility(View.INVISIBLE);
            }
        });

        bt_random.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSelectColor = "彩色";

                check_red.setVisibility(View.INVISIBLE);
                check_orange.setVisibility(View.INVISIBLE);
                check_yellow.setVisibility(View.INVISIBLE);
                check_green.setVisibility(View.INVISIBLE);
                check_blue.setVisibility(View.INVISIBLE);
                check_purple.setVisibility(View.INVISIBLE);
                check_black.setVisibility(View.INVISIBLE);
                check_white.setVisibility(View.INVISIBLE);
                check_random.setVisibility(View.VISIBLE);
            }
        });


        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout */
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(stringResourceForPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addTherapyListContent();

                        /**
                         * Close the dialog fragment
                         */
                        EditListContentDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.negative_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        /**
                         * Close the dialog fragment
                         */
                        EditListContentDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    /**
     * Method to be overriden with whatever edit is supposed to happen to the list
     */
    protected abstract void addTherapyListContent();
}
