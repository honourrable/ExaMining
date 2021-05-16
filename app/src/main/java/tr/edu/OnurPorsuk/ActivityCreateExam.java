package tr.edu.OnurPorsuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ActivityCreateExam extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private EditText timeHourExam;
    private EditText timeMinuteExam;
    private SeekBar difficultySeekBarExam;
    private SeekBar pointSeekBarExam;
    private TextView difficultyValueExam;
    private TextView pointValueExam;
    private ImageButton buttonOK;
    private ImageButton buttonReset;
    private SharedPreferences pref, prefFromSettings;
    private SharedPreferences.Editor editor;
    private String timeHourFromSettings, timeMinuteFromSettings;
    private String difficultyFromSettings, pointFromSettings;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerViewExam;
    QuestionAdapterExam adapter;
    ArrayList<Question> questions;
    ArrayList<Question> selectedQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);
        defineVariables();
        getSharedPreferencesExam();
        setListeners();
    }


    public void setListeners(){

        difficultySeekBarExam.setOnSeekBarChangeListener(this);
        pointSeekBarExam.setOnSeekBarChangeListener(this);

        editor = pref.edit();
        buttonOK.setOnClickListener(v -> {

            // There is a requirement for user to choose at least one question to create
            // exam and save exam's settings if they made any changes different from Settings of app

            // Receiving the selectedQuestions from adapter where checkboxes can be checked and make
            // changes in selectedQuestions ArrayList
            selectedQuestions = adapter.getSelectedQuestions();

            // To check whether at least one question is selected
            if(selectedQuestions.size() != 0){
                editor.putString("Hour", timeHourExam.getText().toString());
                editor.putString("Minute", timeMinuteExam.getText().toString());
                editor.putString("Difficulty", difficultyValueExam.getText().toString());
                editor.putString("Point", pointValueExam.getText().toString());

                int optionNumber = Integer.parseInt(difficultyValueExam.getText().toString());


                //ArrayList<Question> newExam = createExam(optionNumber);
                // newExam will be stored in file


                Toast.makeText(ActivityCreateExam.this, "Exam created successfully",
                        Toast.LENGTH_SHORT).show();
                editor.apply();
            }
            else{
                Toast.makeText(ActivityCreateExam.this, R.string.choose_question,
                        Toast.LENGTH_SHORT).show();
            }
        });

        buttonReset.setOnClickListener(view -> {
            timeHourExam.setText(R.string._00);
            timeMinuteExam.setText(R.string._00);
            difficultySeekBarExam.setProgress(Integer.parseInt("2"));
            difficultyValueExam.setText("2");
            pointSeekBarExam.setProgress(Integer.parseInt("1"));
            pointValueExam.setText("1");
        });
    }

    // Not completed yet
//    private ArrayList<Question> createExam(int optionNumber) {
//        return newExam;
//    }


    private void getSharedPreferencesExam() {
        getSharedPreferencesFromSettings();

          /* This part is provided a choice for programmer to enable users to have chance to save
          Create Exam Activity's settings to SharedPreferences. By commenting these lines, now
          the app uses Settings Activity's settings by default on Create Exam Activity */

//        String timeHour = pref.getString("Hour", timeHourFromSettings);
//        String timeMinute = pref.getString("Minute", timeMinuteFromSettings);
//        String difficulty = pref.getString("Difficulty", difficultyFromSettings);
//        String point = pref.getString("Point", pointFromSettings);

        timeHourExam.setText(timeHourFromSettings);
        timeMinuteExam.setText(timeMinuteFromSettings);
        assert difficultyFromSettings != null;
        difficultySeekBarExam.setProgress(Integer.parseInt(difficultyFromSettings));
        difficultyValueExam.setText(difficultyFromSettings);
        assert pointFromSettings != null;
        pointSeekBarExam.setProgress(Integer.parseInt(pointFromSettings));
        pointValueExam.setText(pointFromSettings);
    }


    private void getSharedPreferencesFromSettings() {
        timeHourFromSettings = prefFromSettings.getString("Hour", "00");
        timeMinuteFromSettings = prefFromSettings.getString("Minute", "00");
        difficultyFromSettings = prefFromSettings.getString("Difficulty", "2");
        pointFromSettings = prefFromSettings.getString("Point", "1");

        timeHourExam.setText(timeHourFromSettings);
        timeMinuteExam.setText(timeMinuteFromSettings);
        assert difficultyFromSettings != null;
        difficultySeekBarExam.setProgress(Integer.parseInt(difficultyFromSettings));
        difficultyValueExam.setText(difficultyFromSettings);
        assert pointFromSettings != null;
        pointSeekBarExam.setProgress(Integer.parseInt(pointFromSettings));
        pointValueExam.setText(pointFromSettings);
    }


    private void defineVariables() {

        timeHourExam = findViewById(R.id.timeHourExam);
        timeMinuteExam = findViewById(R.id.timeMinuteExam);
        difficultySeekBarExam = findViewById(R.id.difficultySeekBarExam);
        difficultyValueExam = findViewById(R.id.difficultySeekBarValueExam);
        pointSeekBarExam = findViewById(R.id.pointSeekBarExam);
        pointValueExam = findViewById(R.id.pointSeekBarValueExam);
        buttonOK = findViewById(R.id.button_ok_exam);
        buttonReset = findViewById(R.id.buttonReset_exam);
        recyclerViewExam = findViewById(R.id.recyclerViewQuestionsExam);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewExam.setLayoutManager(layoutManager);

        questions = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Questions")) {
                questions = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Questions");
            }
            else{
                Toast.makeText(ActivityCreateExam.this, R.string.read_error_from_menu, Toast.LENGTH_SHORT).show();
            }
        }

        selectedQuestions = new ArrayList<>();
        adapter = new QuestionAdapterExam(this, questions, selectedQuestions);
        recyclerViewExam.setAdapter(adapter);

        prefFromSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Context context = getApplicationContext();
        pref = context.getSharedPreferences("Create Exam Preferences", MODE_PRIVATE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

        switch (seekBar.getId()){

            case R.id.difficultySeekBarExam:
                difficultyValueExam.setText(String.valueOf(progress));
                break;
            case R.id.pointSeekBarExam:
                pointValueExam.setText(String.valueOf(progress));
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}