package tr.edu.OnurPorsuk;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.Objects;

public class ActivityExamSettings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private EditText examTimeHour;
    private EditText examTimeMinute;
    private SeekBar difficultySeekBar;
    private SeekBar pointSeekBar;
    private TextView userName;
    private TextView difficultyValue;
    private TextView pointValue;
    private ImageButton buttonOK;
    private ImageButton buttonReset;
    private String name;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_settings);

        name = "User name: " + Objects.requireNonNull(getIntent().getExtras()).getString("Name", "User");
        defineVariables();
        getSharedPreferencesSettings();
        setListeners();
    }


    public void setListeners(){
        difficultySeekBar.setOnSeekBarChangeListener(this);
        pointSeekBar.setOnSeekBarChangeListener(this);

        editor = pref.edit();
        buttonOK.setOnClickListener(v -> {

            editor.putString("Hour", examTimeHour.getText().toString());
            editor.putString("Minute", examTimeMinute.getText().toString());
            editor.putString("Difficulty", difficultyValue.getText().toString());
            editor.putString("Point", pointValue.getText().toString());
            editor.apply();
            Toast.makeText(ActivityExamSettings.this, "Settings upgraded successfully",
                    Toast.LENGTH_SHORT).show();
        });

        buttonReset.setOnClickListener(view -> {
            examTimeHour.setText(R.string._00);
            examTimeMinute.setText(R.string._00);
            difficultySeekBar.setProgress(Integer.parseInt("2"));
            difficultyValue.setText("2");
            pointSeekBar.setProgress(Integer.parseInt("1"));
            pointValue.setText("1");
        });
    }


    private void getSharedPreferencesSettings() {
        String timeHour = pref.getString("Hour", "00");
        String timeMinute = pref.getString("Minute", "00");
        String difficulty = pref.getString("Difficulty", "2");
        String point = pref.getString("Point", "1");

        userName.setText(name);
        examTimeHour.setText(timeHour);
        examTimeMinute.setText(timeMinute);
        assert difficulty != null;
        difficultySeekBar.setProgress(Integer.parseInt(difficulty));
        difficultyValue.setText(difficulty);
        assert point != null;
        pointSeekBar.setProgress(Integer.parseInt(point));
        pointValue.setText(point);
    }

    private void defineVariables() {

        examTimeHour = findViewById(R.id.timeHourSettings);
        examTimeMinute = findViewById(R.id.timeMinuteSettings);
        difficultySeekBar = findViewById(R.id.difficultySeekBarSettings);
        difficultyValue = findViewById(R.id.difficultySeekBarValueSettings);
        pointSeekBar = findViewById(R.id.pointSeekBarSettings);
        pointValue = findViewById(R.id.pointSeekBarValueSettings);
        userName = findViewById(R.id.userNameSettings);
        buttonOK = findViewById(R.id.button_ok_settings);
        buttonReset = findViewById(R.id.buttonReset_settings);

        Context context = getApplicationContext();
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        switch (seekBar.getId()){

            case R.id.difficultySeekBarSettings:
                difficultyValue.setText(String.valueOf(progress));
                break;
            case R.id.pointSeekBarSettings:
                pointValue.setText(String.valueOf(progress));
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