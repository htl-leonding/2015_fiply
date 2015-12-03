package htl_leonding.fiplyteam.fiply;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

    Button startUe;
    Button startTS;
    Button startEU;
    Button startMenu;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    String[] navArray = {"MainActivity", "TrainingSessionActivity", "UebungskatalogActivity", "ErstelleUserActivity", "MenuActivity", "SplashActivity"};
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startUe = (Button) findViewById(R.id.btStartUe);
        startTS = (Button) findViewById(R.id.btStartTr);
        startEU = (Button) findViewById(R.id.btStartEU);
        startMenu = (Button) findViewById(R.id.btStartMenu);
        mDrawerList = (ListView)findViewById(R.id.navlist);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        startUe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openUe = new Intent("fiply.UEBUNGSKATALOGACTIVITY");
                startActivity(openUe);
            }
        });

        startTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openTS = new Intent("fiply.TRAININGSESSIONACTIVITY");
                startActivity(openTS);
            }
        });

        startEU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openEU = new Intent("fiply.ERSTELLEUSERACTIVITY");
                startActivity(openEU);
            }
        });

        startMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMenu = new Intent("fiply.MENUACTIVITY");
                startActivity(openMenu);
            }
        });

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Class classToBeOpened = Class.forName("htl_leonding.fiplyteam.fiply." + navArray[position]);
                    Intent startClass = new Intent(MainActivity.this, classToBeOpened);
                    startActivity(startClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        addDrawerItems();
        setupDrawer();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
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
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
               getActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
