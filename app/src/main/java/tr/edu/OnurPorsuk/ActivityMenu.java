package tr.edu.OnurPorsuk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener{

    private ImageButton listQuestion;
    private ImageButton addQuestion;
    private ImageButton createExam;
    private ImageButton examSettings;
    private String name;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // This part stands for putting user's name for once, when just signed in
        Intent intent = getIntent();
        if(intent.hasExtra("Name")) {
            name = Objects.requireNonNull(intent.getExtras()).getString("Name", "User");
            Toast.makeText(this, "Welcome " + name, Toast.LENGTH_LONG).show();
        }

        defineVariables();
        defineListeners();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.listQuestion:
                intent = new Intent(v.getContext(), ActivityQuestionList.class);
                Bundle bundleListQuestion = new Bundle();
                bundleListQuestion.putParcelableArrayList("All Questions", questions);
                intent.putExtras(bundleListQuestion);
                startActivity(intent);
                break;

            case R.id.addQuestion:
                intent = new Intent(v.getContext(), ActivityQuestionAddUpgrade.class);
                intent.putExtra("Title", "Add Question");
                Bundle bundleAddQuestion = new Bundle();
                bundleAddQuestion.putParcelableArrayList("All Questions", questions);
                intent.putExtras(bundleAddQuestion);
                startActivity(intent);
                break;

            case R.id.createExam:
                intent = new Intent(v.getContext(), ActivityCreateExam.class);
                Bundle bundleCreateExam = new Bundle();
                bundleCreateExam.putParcelableArrayList("All Questions", questions);
                intent.putExtras(bundleCreateExam);
                startActivity(intent);
                break;

            case R.id.examSettings:
                intent = new Intent(v.getContext(), ActivityExamSettings.class);
                intent.putExtra("Name", name);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void defineVariables(){
        listQuestion = findViewById(R.id.listQuestion);
        addQuestion = findViewById(R.id.addQuestion);
        createExam = findViewById(R.id.createExam);
        examSettings = findViewById(R.id.examSettings);
        questions = new ArrayList<>();
        String questionsFileName = "questions.txt";

        // Saving users to file. Since it's already saved, related lines commented
        // questions = Question.getQuestions();
        // writeQuestions(questionsFileName);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Questions")) {
                questions = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Questions");
            }
            else{
                readQuestions(questionsFileName);
            }
        }
    }

    public void writeQuestions(String fileName){
        ObjectOutput out;

        try {
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + fileName;
            out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(questions);
            out.flush();
            out.close();
        } catch (IOException e) {
            Toast.makeText(ActivityMenu.this, R.string.write_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void readQuestions(String fileName){
        ObjectInputStream input;

        try {
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + fileName;
            input = new ObjectInputStream(new FileInputStream(filePath));
            questions = (ArrayList<Question>) input.readObject();

            input.close();
        } catch (ClassNotFoundException | IOException e) {
            Toast.makeText(ActivityMenu.this, R.string.read_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void defineListeners(){
        listQuestion.setOnClickListener(this);
        addQuestion.setOnClickListener(this);
        createExam.setOnClickListener(this);
        examSettings.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.icon_alert);
        alert.setTitle("Exit");
        alert.setMessage(R.string.exit_menu);
        alert.setPositiveButton(R.string.yes, (dialog, which) -> {
            startActivity(new Intent(ActivityMenu.this, ActivityMain.class));
            finish();
        });
        alert.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel());
        alert.show();
    }
}