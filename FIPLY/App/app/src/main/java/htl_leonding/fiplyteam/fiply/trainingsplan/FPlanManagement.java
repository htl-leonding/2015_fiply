package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.sql.SQLException;
import java.util.ArrayList;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.InstruktionenEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PlanEntry;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.data.PlanRepository;


public class FPlanManagement extends Fragment {

    ArrayList<Trainingsplanlistitem> arrayOfPlans;
    PlanRepository planRep;
    Cursor c;
    ImageButton addButton;
    ImageButton deleteButton;
    ImageButton exportCSV;
    ImageButton exportPDF;
    KeyValueRepository Krep;

    int selectedItem = -1;
    int previousItem = -1;

    ListView actualView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleplanmanagement));
        PlanRepository.setContext(getContext());
        planRep = PlanRepository.getInstance();
        KeyValueRepository.setContext(getContext());
        Krep = KeyValueRepository.getInstance();
        return inflater.inflate(R.layout.fragment_planmanagement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayOfPlans = new ArrayList<Trainingsplanlistitem>();
        arrayOfPlans = initPlans();
        final ListView planView = (ListView) getActivity().findViewById(R.id.listViewtrainingsplan);
        PlanAdapter adapter = new PlanAdapter(getActivity(),R.layout.trainingsplan_item, arrayOfPlans);
        actualView = planView;
        planView.setAdapter(adapter);

        deleteButton = (ImageButton) getActivity().findViewById(R.id.deleteplan);
        addButton = (ImageButton) getActivity().findViewById(R.id.addplan);
        exportCSV = (ImageButton) getActivity().findViewById(R.id.exportcsvbt);
        exportPDF = (ImageButton) getActivity().findViewById(R.id.exportpdfbt);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FTrainingsplan fragment = new FTrainingsplan();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fraPlace, fragment);
                fragmentTransaction.commit();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.fertig)
                        .setMessage(R.string.deleteplanquastion)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deletePlan();
                            }
                        })
                        .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.successsmall)
                        .show();
            }
        });

        planView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeFocus(planView, position);
            }
        });

        view.post(new Runnable() {
            @Override
            public void run() {
                try {
                    changeFocus(planView, Integer.valueOf(Krep.getKeyValue("selectedPlan").getString(1)));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    AdView mAdView = (AdView) getActivity()
            .findViewById(R.id.planAdView);
    int gender;
    try {
        gender = Krep.getGender();
    } catch (SQLException e) {
        gender = AdRequest.GENDER_UNKNOWN;
    }
    AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("BAD0FD4427FB4415FF62FD7B3D3B655D")
            .setGender(gender)
            .build();
    mAdView.loadAd(adRequest);
    }

    private void deletePlan() {
        PlanRepository planRep;
        InstruktionenRepository instruktRep;
        PhasenRepository phasenRep;
        PlanRepository.setContext(getContext());
        InstruktionenRepository.setContext(getContext());
        PhasenRepository.setContext(getContext());

        planRep = PlanRepository.getInstance();
        instruktRep = InstruktionenRepository.getInstance();
        phasenRep = PhasenRepository.getInstance();

        Trainingsplanlistitem item = (Trainingsplanlistitem) actualView.getItemAtPosition(selectedItem);
        String planId = null;
        System.out.println("finished");

        int iRowId = c.getColumnIndex(PlanEntry.COLUMN_ROWID);
        Cursor j = planRep.getPlanByName(item.getName());
        System.out.println("finished");

        j.moveToFirst();
        planId = j.getString(iRowId);
        System.out.println("finished");

        String[] phasenIds = null;
        Cursor c = phasenRep.getIdsByPlanId(planId);
        System.out.println("finished");
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            instruktRep.deleteByPhasenId(c.getString(0));
        }
        System.out.println("finished");

        phasenRep.deleteByPlanId(planId);
        planRep.deleteByPlanId(planId);
        System.out.println("finished");

        selectedItem = -1;
        previousItem = -1;
        Krep.updateKeyValue("selectedPlan", String.valueOf(selectedItem));
        getFragmentManager().popBackStack();
    }

    public void changeFocus(ListView planView, int newSelection){
        if (newSelection == -1 || newSelection == selectedItem || planView == null){
            return;
        }
        selectedItem = newSelection;
        int pos = planView.getFirstVisiblePosition();

        System.out.println(selectedItem);
        System.out.println(pos);
        System.out.println(planView.getCount());

        planView.getChildAt(selectedItem + pos).setBackgroundResource(R.color.darkselected);
        planView.getChildAt(selectedItem + pos).setBackgroundResource(R.drawable.planlistitemborder);
        if (previousItem != -1) {
             planView.getChildAt(previousItem + pos).setBackgroundResource(R.drawable.unselecteditem);
        }

        previousItem = selectedItem;
        Krep.updateKeyValue("selectedPlan", String.valueOf(selectedItem));
    }

    private ArrayList<Trainingsplanlistitem> initPlans() {
        c = planRep.getAllPlans();
        ArrayList<Trainingsplanlistitem> plans = new ArrayList<Trainingsplanlistitem>();

        int iPlanName = c.getColumnIndex(PlanEntry.COLUMN_PLANNAME);
        int iStartDate = c.getColumnIndex(PlanEntry.COLUMN_STARTDATE);
        int iEndDate = c.getColumnIndex(PlanEntry.COLUMN_ENDDATE);
        int iZiel = c.getColumnIndex(PlanEntry.COLUMN_ZIEL);

        String planName, startDate, endDate, ziel;
        Trainingsplanlistitem item;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            planName = c.getString(iPlanName);
            startDate = c.getString(iStartDate);
            endDate = c.getString(iEndDate);
            ziel = c.getString(iZiel);

            item = new Trainingsplanlistitem(planName, startDate, endDate, ziel);
            plans.add(item);
        }

        return plans;
    }
}
