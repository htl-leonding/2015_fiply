package htl_leonding.fiplyteam.fiply.menu;

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

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.music.FPlaylist;
import htl_leonding.fiplyteam.fiply.trainingsplan.FTrainingsplan;
import htl_leonding.fiplyteam.fiply.trainingssession.FSettings;
import htl_leonding.fiplyteam.fiply.uebungskatalog.FUebungskatalog;
import htl_leonding.fiplyteam.fiply.user.FUsermanagement;

public class MainActivity extends AppCompatActivity {
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    String mActivityTitle;
    String[] navArray = new String[6];

    /**
     * Wird beim Ersten Aufruf der MainActivity aufgerufen und dient dem setzen der OnClickListener
     *
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView) findViewById(R.id.navlist);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        Resources res = getResources();
        navArray = res.getStringArray(R.array.navigationArray);

        /**
         * Bei Drücken eines Elements des NavigationDrawers wird eine FragmentTransaction durchgeführt,
         * in der das Fragment des gedrückten Elements in das FrameLayout der MainActivity geladen wird
         */
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });

        //Übergibt dem NavigationDrawer die Elemente die er enthalten soll
        addDrawerItems();
        //Konfiguriert den navigationDrawer
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Lädt das FMain Fragment in das FrameLayout der MainActivity
        FMain fMain = new FMain();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fraPlace, fMain).commit();
    }

    /**
     * Wird nachdem erstellen der View aufgerufen und stellt sicher
     * dass der NavigationDrawer und die Activity synchron sind
     *
     * @param savedInstanceState default
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * Teilt der DrawerNavigation mit wenn sich die Configuration ändert
     *
     * @param newConfig default
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Übergibt dem NavigationDrawer die Elemente die er enthalten soll
     */
    private void addDrawerItems() {
        mAdapter = new ArrayAdapter<>(this, R.layout.navigation_list, R.id.navlist_content, navArray);
        mDrawerList.setAdapter(mAdapter);
    }

    /**
     * Konfiguriert den NavigationDrawer und behandelt das Verhalten beim öffnen und schließen
     */
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /**
             * Behandelt das Verhalten beim Öffnen des NavigationDrawers
             *
             * @param drawerView default
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            /**
             * Behandelt das Verhalten beim Schließen des NavigationDrawers
             * @param view default
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Teilt dem NavigationDrawer mit wenn ein Item gefrückt wird
     *
     * @param item Fragment das ins FrameLayout der MainActivity geladen werden soll
     * @return Fragment das ins FrameLayout der MainActivity geladen werden soll
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Lädt das Fragment, dass sich an der jeweiligen Position in der Liste befindet, in das FrameLayout der MainActivity
     *
     * @param position Position in der Liste des NavigationDrawers
     */
    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FMain();
                break;
            case 1:
                fragment = new FSettings();
                break;
            case 2:
                fragment = new FUsermanagement();
                break;
            case 3:
                fragment = new FTrainingsplan();
                break;
            case 4:
                fragment = new FUebungskatalog();
                break;
            case 5:
                fragment = new FPlaylist();
                break;
            default:
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, fragment);
        fragmentTransaction.commit();

        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
