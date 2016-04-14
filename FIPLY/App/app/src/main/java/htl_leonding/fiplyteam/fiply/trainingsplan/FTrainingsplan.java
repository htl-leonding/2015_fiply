package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.data.PlanRepository;

import static htl_leonding.fiplyteam.fiply.data.FiplyContract.*;

public class FTrainingsplan extends Fragment {

    Context context;
    ToggleButton tButton;

    RadioButton rbMuskelaufbau;
    RadioButton rbMaximalkraft;
    RadioButton rbCardio;
    Button setDatePick;
    Button generate;
    int progress = 0;

    KeyValueRepository rep;

    Boolean isSelected = false;
    ProgressBar pBar;

    Calendar myCalendar = Calendar.getInstance();

    Spinner dayone;
    Spinner daytwo;
    Spinner daythree;

    String firstday = "";
    String secondday = "";
    String thirdday = "";

    boolean firstSet = false;
    boolean secondSet = false;
    boolean thirdSet = false;

    String lastDayOne;
    String lastDayTwo;
    String lastDayThree;

    String[] days = new String[]{"Auswählen", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
    String[] actualdays;
    Date startDate;

    InstruktionenRepository instRep;
    PhasenRepository phasenRep;
    PlanRepository planRep;
    List<Trainingsphase> trainingsphaseList;
    private String planName = "";
    String ziel = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        getActivity().setTitle(R.string.generateTitle);
        return inflater.inflate(R.layout.fragment_trainingsplantest, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tButton = (ToggleButton) getActivity().findViewById(R.id.toggleButton);
        tButton.setGravity(Gravity.TOP);
        tButton.setPadding(0, 10, 0, 0);
        pBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        Drawable draw = getActivity().getDrawable(R.drawable.progressbar);
        pBar.setProgressDrawable(draw);
        rbMaximalkraft = (RadioButton) getActivity().findViewById(R.id.radioButtonMaximalKraft);
        rbMuskelaufbau = (RadioButton) getActivity().findViewById(R.id.radioButtonMuskelaufbau);
        rbCardio = (RadioButton) getActivity().findViewById(R.id.radioButtonGesundheit);
        setDatePick = (Button) getActivity().findViewById(R.id.btsetDatePick);
        generate = (Button) getActivity().findViewById(R.id.generateButton);
        generate.setVisibility(View.INVISIBLE);
        dayone = (Spinner) getActivity().findViewById(R.id.spinnerdayone);
        daytwo = (Spinner) getActivity().findViewById(R.id.spinnerdaytwo);
        daythree = (Spinner) getActivity().findViewById(R.id.spinnerdaythree);
        setDatePicker();
        ArrayAdapter<String> dayArrAdapter = new ArrayAdapter<>(getActivity(), R.layout.dayofweek, days);
        dayone.setAdapter(dayArrAdapter);
        daytwo.setAdapter(dayArrAdapter);
        daythree.setAdapter(dayArrAdapter);
        addProgressChecked(20);
        KeyValueRepository.setContext(getActivity());
        InstruktionenRepository.setContext(getActivity());
        PhasenRepository.setContext(getActivity());
        rep = KeyValueRepository.getInstance();
        instRep = InstruktionenRepository.getInstance();
        phasenRep = PhasenRepository.getInstance();
        PlanRepository.setContext(getActivity());
        planRep = PlanRepository.getInstance();
        try {
            if (rep.getKeyValue("userProf").equals("Not Fit")) {
                tButton.setChecked(true);
            } else {
                tButton.setChecked(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rbMaximalkraft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbMuskelaufbau.setChecked(false);
                    rbCardio.setChecked(false);
                    if (!isSelected) {
                        isSelected = true;
                        addProgressChecked(30);
                    }
                }
            }
        });

        rbMuskelaufbau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbMaximalkraft.setChecked(false);
                    rbCardio.setChecked(false);
                    if (!isSelected) {
                        isSelected = true;
                        addProgressChecked(30);
                    }
                }
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateClicked();
            }
        });
        rbCardio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbMuskelaufbau.setChecked(false);
                    rbMaximalkraft.setChecked(false);
                    if (!isSelected) {
                        isSelected = true;
                        addProgressChecked(30);
                    }
                }

            }
        });


        dayone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastDayOne = firstday;
                firstday = days[position];
                if (!firstSet && lastDayOne == days[0]) {
                    addProgressChecked(10);
                    firstSet = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                firstSet = false;
            }
        });
        daytwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastDayTwo = secondday;
                secondday = days[position];
                if (!secondSet && lastDayTwo == days[0]) {
                    addProgressChecked(10);
                    secondSet = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                secondSet = false;
            }
        });
        daythree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastDayThree = thirdday;
                thirdday = days[position];
                if (!thirdSet && lastDayThree == days[0]) {
                    addProgressChecked(10);
                    thirdSet = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                thirdSet = false;
            }
        });

    }

    // Generiert einen Trainingsplan
    private void generateClicked() {
        if (rbMuskelaufbau.isChecked()) {
            trainingsphaseList = new LinkedList<Trainingsphase>();
            GenerateAllgemein allgemein = new GenerateAllgemein(!tButton.isChecked(), 1, actualdays, startDate);
            trainingsphaseList.add(allgemein.getTPhase());
            GeneratePhTwoMuskelPh3Kraft phaseZweiMuskel = new GeneratePhTwoMuskelPh3Kraft(actualdays, allgemein.getTPhase().getEndDate(), "Muskelaufbau", new String[]{"Bauch", "Beine", "Brust"});
            trainingsphaseList.add(phaseZweiMuskel.getTPhase());
            GeneratePhTwoMaxiPh3Muskel phaseDreiMuskel = new GeneratePhTwoMaxiPh3Muskel(phaseZweiMuskel.getTPhase().getEndDate(), "Muskelaufbau", actualdays);
            trainingsphaseList.add(phaseDreiMuskel.getTPhase());
            this.ziel = getResources().getString(R.string.trainingszielMuskelaufbau);
        } else if (rbMaximalkraft.isChecked()) {
            trainingsphaseList = new LinkedList<Trainingsphase>();
            GenerateAllgemein allgemein = new GenerateAllgemein(!tButton.isChecked(), 1, actualdays, startDate);
            trainingsphaseList.add(allgemein.getTPhase());
            GeneratePhTwoMaxiPh3Muskel phaseZweiMaximalkraft = new GeneratePhTwoMaxiPh3Muskel(allgemein.getTPhase().getEndDate(), "Maximalkraft", actualdays);
            trainingsphaseList.add(phaseZweiMaximalkraft.getTPhase());
            GeneratePh3Maxi phaseDreiMaximimalkraft = new GeneratePh3Maxi(phaseZweiMaximalkraft.getTPhase().getEndDate(), actualdays);
            trainingsphaseList.add(phaseDreiMaximimalkraft.getTPhase());
            this.ziel = getResources().getString(R.string.trainingszielMaximalKraft);
        } else if (rbCardio.isChecked()) {
            trainingsphaseList = new LinkedList<Trainingsphase>();
            GenerateAllgemein allgemein = new GenerateAllgemein(!tButton.isChecked(), 1, actualdays, startDate);
            trainingsphaseList.add(allgemein.getTPhase());
            GeneratePhTwoKraftausdauer phaseZweiKraftausdauer = new GeneratePhTwoKraftausdauer(actualdays, allgemein.getTPhase().getEndDate(), true);
            trainingsphaseList.add(phaseZweiKraftausdauer.getTPhase());
            GeneratePhTwoMaxiPh3Muskel phaseDreiMuskel = new GeneratePhTwoMaxiPh3Muskel(phaseZweiKraftausdauer.getTPhase().getEndDate(), "Kraftausdauer", actualdays);
            trainingsphaseList.add(phaseDreiMuskel.getTPhase());
            this.ziel = getResources().getString(R.string.trainingszielGesundheit);
        }
        askForName();
    }

    // Popup, das nach dem Namen fragt wird hier erstellt.
    private String askForName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Name wählen:");
        builder.setIcon(R.drawable.questionsmall);
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                planName = input.getText().toString();
                writeToDataBase();
                success();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        return planName;
    }

    // Erfolgsmeldung
    private void success() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.fertig)
                .setMessage(R.string.successmessagetrain)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(R.drawable.successsmall)
                .show();
        final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_revert);
        generate.startAnimation(animTranslate);
        generate.setVisibility(View.INVISIBLE);
        getFragmentManager().popBackStack();
    }

    // Der Trainingsplan wird in die Datenbank geschrieben.
    private void writeToDataBase() {
        DateFormat format = new SimpleDateFormat("dd. MMMM yyyy", Locale.ENGLISH);

        String planStartDate = format.format(trainingsphaseList.get(0).getStartDate());
        String planEndDate = format.format(trainingsphaseList.get(trainingsphaseList.size() - 1).getEndDate());
        planRep.insertPlan(planName, planStartDate, planEndDate, ziel);
        Cursor cplan = planRep.getPlanByName(planName);
        cplan.moveToFirst();
        int iPlanId = cplan.getColumnIndex(PlanEntry.COLUMN_ROWID);
        String planId = cplan.getString(iPlanId);

        for (Trainingsphase phase : trainingsphaseList) {

            String dbStartDate = format.format(phase.getStartDate());
            String dbEndDate = format.format(phase.getEndDate());
            phasenRep.insertPhase(dbStartDate, dbEndDate,
                    phase.getPhasenName(), String.valueOf(phase.getPhasenDauer()), String.valueOf(phase.getPausenDauer()),
                    String.valueOf(phase.getSaetze()), String.valueOf(phase.getWiederholungen()), planId);

            Cursor c = phasenRep.getPhaseByStartDate(startDate);
            c.moveToFirst();
            int index = c.getColumnIndex(PhasenEntry.COLUMN_ROWID);
            String rowid = c.getString(index);
            for (Uebung ueb : phase.getUebungList()) {
                instRep.insertUebung(ueb.getWochenTag(), String.valueOf(ueb.getRepmax()), ueb.getUebungsID(), rowid);
            }
        }
    }

    // Updated die Progressbar in dem Fragment
    private void addProgressChecked(int howMuch) {
        if ((pBar.getProgress() + howMuch) <= 100) {
            progress = pBar.getProgress() + howMuch;
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                ObjectAnimator animation = ObjectAnimator.ofInt(pBar, "progress", progress);
                animation.setDuration(500);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
            } else
                pBar.setProgress(pBar.getProgress() + howMuch);
        }
        if (progress == 100) {
            if (Validation()) {
                final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
                generate.startAnimation(animTranslate);
                generate.setVisibility(View.VISIBLE);
            }
        }
    }

    // Eingabeüberprüfungen
    public boolean Validation() {
        String xday = dayone.getSelectedItem().toString();
        String yday = daytwo.getSelectedItem().toString();
        String zday = daythree.getSelectedItem().toString();

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        boolean isToday = (myCalendar.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                                myCalendar.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                myCalendar.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
        if (!xday.equals(days[0]) && !yday.equals(days[0]) && !zday.equals(days[0])
                && (myCalendar.getTime().after(new Date()) || isToday)) {
            actualdays = new String[]{xday, yday, zday};
            return true;
        } else {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.fehler)
                    .setMessage(R.string.checkInput)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.drawable.alertsmall)
                    .show();
            final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_revert);
            generate.startAnimation(animTranslate);
            generate.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    // Setzt den Datepicker im Fragment Formular
    private void setDatePicker() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

                addProgressChecked(20);
            }
        };

        setDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.datepicker, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    // Aktualisiert die Datumsanzeige
    private void updateLabel() {
        String myFormat = "dd. MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);
        setDatePick.setText("Starttermin: " + sdf.format(myCalendar.getTime()));
        startDate = myCalendar.getTime();
    }
}
