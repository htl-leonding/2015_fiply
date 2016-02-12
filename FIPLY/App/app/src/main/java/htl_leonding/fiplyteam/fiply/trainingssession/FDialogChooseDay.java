package htl_leonding.fiplyteam.fiply.trainingssession;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import htl_leonding.fiplyteam.fiply.R;

public class FDialogChooseDay extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final String[] wochentage = savedInstanceState.getStringArray("days");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choosedaybt)
                .setItems(wochentage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        savedInstanceState.putString("day", wochentage[which]);
                    }
                });
        return builder.create();
    }
}
