package htl_leonding.fiplyteam.fiply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button startUe;
    Button startTS;
    Button startEU;
    Button startMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startUe = (Button) findViewById(R.id.btStartUe);
        startTS = (Button) findViewById(R.id.btStartTr);
        startEU = (Button) findViewById(R.id.btStartEU);
        startMenu = (Button) findViewById(R.id.btStartMenu);

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
                Intent openMenu = new Intent("fiply.MENU");
                startActivity(openMenu);
            }
        });

        //TEST
    }
}
