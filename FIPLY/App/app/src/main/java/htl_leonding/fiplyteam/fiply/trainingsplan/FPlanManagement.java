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
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PlanEntry;
import htl_leonding.fiplyteam.fiply.data.PlanRepository;


public class FPlanManagement extends Fragment {

    ArrayList<Trainingsplanlistitem> arrayOfPlans;
    ListView planView;
    PlanRepository planRep;
    Cursor c;
    ImageButton addButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleplanmanagement));
        PlanRepository.setContext(getContext());
        planRep = PlanRepository.getInstance();
        return inflater.inflate(R.layout.fragment_planmanagement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayOfPlans = new ArrayList<Trainingsplanlistitem>();
        arrayOfPlans = initPlans();
        planView = (ListView) getActivity().findViewById(R.id.listViewtrainingsplan);

        PlanAdapter adapter = new PlanAdapter(getActivity(),R.layout.trainingsplan_item, arrayOfPlans);

        planView.setAdapter(adapter);

        addButton = (ImageButton) getActivity().findViewById(R.id.addplan);

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
