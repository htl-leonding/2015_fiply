package htl_leonding.fiplyteam.fiply;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import htl_leonding.fiplyteam.fiply.fragments.FMain;
import htl_leonding.fiplyteam.fiply.fragments.FTrainingssession;
import htl_leonding.fiplyteam.fiply.fragments.FUebungskatalog;
import htl_leonding.fiplyteam.fiply.fragments.FUsererstellung;

public class MainActivity extends AppCompatActivity {
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    String mActivityTitle;
    String[] navArray = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView) findViewById(R.id.navlist);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        Resources res = getResources();
        navArray = res.getStringArray(R.array.navigationArray);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*
                try {
                }
                    Class classToBeOpened = Class.forName("htl_leonding.fiplyteam.fiply." + navArray[position] + "Activity");
                    Intent startClass = new Intent(MainActivity.this, classToBeOpened);
                    startActivity(startClass);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                */
                displayView(position);
            }
        });

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FMain fMain = new FMain();
        fragmentTransaction.replace(R.id.fraPlace, fMain);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void addDrawerItems() {
        mAdapter = new ArrayAdapter<>(this, R.layout.navigation_list, R.id.navlist_content, navArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FMain();
                break;
            case 1:
                fragment = new FTrainingssession();
                break;
            case 2:
                fragment = new FUebungskatalog();
                break;
            case 3:
                fragment = new FUsererstellung();
                break;

            default:
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fraPlace, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
