package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class FUsermanagement extends Fragment {

//    Button btCreate;
//    Button btEdit;
//    Button btChoose;
    Button btNext;
    Button btPrev;
    int pageId = 1;
    KeyValueRepository kvr;

    /**
     * Lädt das fragment_usererstellung in das FrameLayout der MainActivity
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usermanagement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        KeyValueRepository.setContext(getContext());
        kvr = KeyValueRepository.getInstance();

        btNext = (Button) getActivity().findViewById(R.id.btUserNext);
        btPrev = (Button) getActivity().findViewById(R.id.btUserPrevious);

        btNext.setText("Next");
        btPrev.setText("Cancel");

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getPageId()){
                    case 1:
                        btPrev.setText("Previous");
                        FCreateUser f = (FCreateUser) getFragmentManager().findFragmentByTag("NamePage");
                        kvr.insertKeyValue("firstName", f.getFirstName());
                        kvr.insertKeyValue("lastName", f.getLastName());
                        displayViewP2();
                        break;
                    case 2:
                        displayViewP3();
                        FCreateUser2 a = (FCreateUser2) getFragmentManager().findFragmentByTag("SliderPage");
                        kvr.insertKeyValue("userHeight", a.getHeight());
                        kvr.insertKeyValue("userWeight", a.getWeight());

                        break;
                    case 3:
                        btNext.setText("Save");
                        FCreateUser3 s = (FCreateUser3) getFragmentManager().findFragmentByTag("AgePage");
                        kvr.insertKeyValue("userDateOfBirth", s.getDateOfBirth());

                        displayViewP4();
                        break;
                    case 4:
                        displayViewPMain();
                        FCreateUser4 p = (FCreateUser4) getFragmentManager().findFragmentByTag("proficiencyPage");
                        kvr.insertKeyValue("userProficiency", p.getProficiency());
                        break;
                }
            }
        });

        btPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getPageId()){
                    case 1: displayViewPMain();
                        break;
                    case 2: displayViewP1();
                        btPrev.setText("Cancel");
                        break;
                    case 3: displayViewP2();
                        break;
                    case 4: displayViewP3();
                        btNext.setText("Next");
                        break;
                    default:
                }
            }
        });

        displayViewP1();

        super.onViewCreated(view, savedInstanceState);
    }

    private void displayViewP1() {
        setPageId(1);
        FCreateUser fragment = new FCreateUser();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraUserInput, fragment, "NamePage");
        fragmentTransaction.commit();
    }
    private void displayViewP2() {
        setPageId(2);
        FCreateUser2 fragment = new FCreateUser2();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraUserInput, fragment, "AgePage");
        fragmentTransaction.commit();
    }
    private void displayViewP3() {
        setPageId(3);
        FCreateUser3 fragment = new FCreateUser3();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraUserInput, fragment, "SliderPage");
        fragmentTransaction.commit();
    }
    private void displayViewP4() {
        setPageId(4);
        FCreateUser4 fragment = new FCreateUser4();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraUserInput, fragment, "Proficiency");
        fragmentTransaction.commit();
    }
    private void displayViewPMain() {
        FMain fragment = new FMain();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, fragment);
        fragmentTransaction.commit();
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}