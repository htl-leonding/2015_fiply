package htl_leonding.fiplyteam.fiply;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ListActivity {

    String classes[] = {"MainActivity", "TrainingSessionActivity", "UebungskatalogActivity", "ErstelleUserActivity", "MenuActivity", "SplashActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_list_item_1, classes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        try {
            Class classToBeOpened = Class.forName("htl_leonding.fiplyteam.fiply." + classes[position]);
            Intent startClass = new Intent(MenuActivity.this, classToBeOpened);
            startActivity(startClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
