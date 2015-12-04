package htl_leonding.fiplyteam.fiply;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    Button startUe;
    Button startTS;
    Button startEU;
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    String[] navArray = {"Main", "TrainingSession", "Uebungskatalog", "ErstelleUser", "Splash"};
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
        mDrawerList = (ListView) findViewById(R.id.navlist);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Class classToBeOpened = Class.forName("htl_leonding.fiplyteam.fiply." + navArray[position] + "Activity");
                    Intent startClass = new Intent(MainActivity.this, classToBeOpened);
                    startActivity(startClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        addDrawerItems();
        setupDrawer();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
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
        mAdapter = new ArrayAdapter<>(this, R.layout.navlist_content, R.id.navlist_content, navArray);
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
}
