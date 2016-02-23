package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PlanEntry;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PlanRepository;


public class FPlanManagement extends Fragment {

    ArrayList<Trainingsplanlistitem> arrayOfPlans;
    PlanRepository planRep;
    Cursor c;
    ImageButton addButton;
    ImageButton exportCSV;
    ImageButton exportPDF;

    KeyValueRepository Krep;

    int selectedItem = -1;
    int previousItem = -1;

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

        planView.setAdapter(adapter);

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
    }

    public void changeFocus(ListView planView, int newSelection){
        if (newSelection == -1 || newSelection == selectedItem || planView == null){
            return;
        }
        selectedItem = newSelection;
        int pos = planView.getFirstVisiblePosition();

        planView.getChildAt(selectedItem + pos).setBackgroundResource(R.color.darkselected);
        planView.getChildAt(selectedItem + pos).setBackgroundResource(R.drawable.planlistitemborder);
        planView.getChildAt(selectedItem + pos).findViewById(R.id.imageButtonInfo).setBackgroundResource(R.color.darkselected);
        if (previousItem != -1) {
            planView.getChildAt(previousItem + pos).findViewById(R.id.imageButtonInfo).setBackgroundResource(R.color.darkSecondary);
            planView.getChildAt(previousItem + pos).setBackgroundResource(R.drawable.unselecteditem);
        }

        previousItem = selectedItem;
        Krep.updateKeyValue("selectedPlan", String.valueOf(selectedItem));
        Trainingsplanlistitem plan = (Trainingsplanlistitem) planView.getItemAtPosition(selectedItem);
        Cursor c = planRep.getPlanByName(plan.getName());
        c.moveToFirst();
        int iPlanid = c.getColumnIndex(PlanEntry.COLUMN_ROWID);
        String planId = c.getString(iPlanid);
        Krep.updateKeyValue("selectedPlanId", planId);
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
