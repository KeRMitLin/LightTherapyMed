package com.kermitlin.lighttherapymed.ui;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kermitlin.lighttherapymed.R;
import com.kermitlin.lighttherapymed.ui.deployedLists.DeployedListsFragment;
import com.kermitlin.lighttherapymed.ui.operatingLists.OperatingListsFragment;
import com.kermitlin.lighttherapymed.ui.therapyLists.AddTherapyListDialogFragment;
import com.kermitlin.lighttherapymed.ui.therapyLists.TherapyListsFragment;
import com.kermitlin.lighttherapymed.ui.usersLists.UsersListsFragment;
import com.kermitlin.lighttherapymed.utils.Constants;

public class DeployedActivity extends BaseActivity {

    private String mEncodedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployed);

        /* Get the push ID from the extra passed by ShoppingListFragment */
        Intent intent = this.getIntent();
        mEncodedEmail = intent.getStringExtra(Constants.KEY_ENCODED_EMAIL);
        if (mEncodedEmail == null) {
            /* No point in continuing without a valid ID. */
            finish();
            return;
        }

        initializeScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void initializeScreen() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
         **/
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        /**
         * Setup the mTabLayout with view pager
         */
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Use positions (0 and 1) to find and instantiate fragments with newInstance()
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:
                    fragment = DeployedListsFragment.newInstance(mEncodedEmail);
                    break;
                case 1:
                    fragment = OperatingListsFragment.newInstance(mEncodedEmail);
                    break;
                default:
                    fragment = DeployedListsFragment.newInstance(mEncodedEmail);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        /**
         * Set string resources as titles for each fragment by it's position
         *
         * @param position
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pager_title_deployed_lists);
                case 1:
                default:
                    return getString(R.string.pager_title_operating_lists);
            }
        }
    }
}
