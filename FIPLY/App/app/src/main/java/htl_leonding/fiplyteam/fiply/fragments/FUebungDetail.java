package htl_leonding.fiplyteam.fiply.fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Gerildo on 08.12.2015.
 */
public class FUebungDetail extends Fragment {

    String uebungsName;
    String uebungsVideo;
    TextView tvUebungName;
    VideoView vvUebungVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_uebungdetail, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uebungsName = getArguments().getString("name");
        uebungsVideo = getArguments().getString("video");

        tvUebungName = (TextView) getView().findViewById(R.id.detailUebungName);
        vvUebungVideo = (VideoView) getView().findViewById(R.id.detailUebungVideo);


        vvUebungVideo.setVideoURI(Uri.parse("https://youtu.be/pXRviuL6vMY"));
        tvUebungName.setText(uebungsName);
        vvUebungVideo.start();
    }
}
