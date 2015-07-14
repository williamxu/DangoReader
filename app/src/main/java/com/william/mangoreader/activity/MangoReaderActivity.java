package com.william.mangoreader.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.william.mangoreader.R;
import com.william.mangoreader.fragment.BrowseMangaFragment;
import com.william.mangoreader.fragment.MyLibraryFragment;
import com.william.mangoreader.fragment.NavDrawerFragment;

public class MangoReaderActivity extends AppCompatActivity implements NavDrawerFragment.NavDrawerFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mango_reader);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // spinner for source selection
        Spinner spinner = (Spinner) findViewById(R.id.spinner_browse_sources);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.browse_sources, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.drop_list);
        spinner.setAdapter(adapter);

        NavDrawerFragment drawerFragment = (NavDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    /**
     * Handles what fragment to display based on navdrawer selection.
     *
     * @param position Position of clicked view in navdrawer.
     */
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        // create corresponding fragment
        switch (position) {
            case 0: // library
                findViewById(R.id.spinner_browse_sources).setVisibility(View.GONE);
                fragment = new MyLibraryFragment();
                title = getString(R.string.title_my_library);
                break;
            case 1: // browse
                findViewById(R.id.spinner_browse_sources).setVisibility(View.VISIBLE);
                fragment = new BrowseMangaFragment();
                title = getString(R.string.title_browse);
                break;
            case 2: // settings
                Intent settingsIntent = new Intent(MangoReaderActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return;
            default:
                break;
        }
        // add fragment to MangoReaderActivity #container_body
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }
}
