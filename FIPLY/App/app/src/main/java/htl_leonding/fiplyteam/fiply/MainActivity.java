package htl_leonding.fiplyteam.fiply;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    Button startUe;
    Button startTr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startUe = (Button) findViewById(R.id.btStartUe);
        startTr = (Button) findViewById(R.id.btStartTr);
    }


}
