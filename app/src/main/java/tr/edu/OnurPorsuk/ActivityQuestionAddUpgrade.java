package tr.edu.OnurPorsuk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityQuestionAddUpgrade extends AppCompatActivity {

    private EditText questionText;
    private EditText optionA;
    private EditText optionB;
    private EditText optionC;
    private EditText optionD;
    private EditText optionE;
    private EditText answer;
    private ImageButton buttonAttachFile;
    private ImageButton buttonOK;
    private ImageButton buttonReset;
    private int questionNo;
    private String filePath;
    private final static int SELECT_FILE = 12345;
    private ArrayList<Question> questions;
    private String fromWhichActivity;
    private final ArrayList<EditText> editTexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add_upgrade);
        defineVariables();
        defineListeners();
    }

    private void defineListeners() {

        buttonOK.setOnClickListener(v -> {
            Intent intent;
            String questionsFileName = "questions.txt";
            
            if(fromWhichActivity.equals("Add Question")){
                if(isFilled()){
                    intent = new Intent(v.getContext(), ActivityMenu.class);
                    Question question = new Question(questionText.getText().toString(),
                            optionA.getText().toString(), optionB.getText().toString(),
                            optionC.getText().toString(), optionD.getText().toString(),
                            optionE.getText().toString(), answer.getText().toString());

                    if(filePath != null){
                        question.setFilePath(filePath);
                    }

                    questions.add(question);

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("All Questions", questions);
                    intent.putExtras(bundle);

                    writeQuestions(questionsFileName);

                    Toast.makeText(ActivityQuestionAddUpgrade.this, R.string.add_question_ok,
                            Toast.LENGTH_SHORT).show();
                    cleanTextBox();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ActivityQuestionAddUpgrade.this, R.string.enter_info,
                            Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(isFilled()) {
                    intent = new Intent(v.getContext(), ActivityQuestionList.class);
                    
                    questions.get(questionNo).setQuestion(questionText.getText().toString());
                    questions.get(questionNo).setOptionA(optionA.getText().toString());
                    questions.get(questionNo).setOptionB(optionB.getText().toString());
                    questions.get(questionNo).setOptionC(optionC.getText().toString());
                    questions.get(questionNo).setOptionD(optionD.getText().toString());
                    questions.get(questionNo).setOptionE(optionE.getText().toString());
                    questions.get(questionNo).setAnswer(answer.getText().toString());
                    questions.get(questionNo).setFilePath(filePath);

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("All Questions", questions);
                    intent.putExtras(bundle);

                    writeQuestions(questionsFileName);

                    Toast.makeText(ActivityQuestionAddUpgrade.this, R.string.upgrade_question_ok,
                            Toast.LENGTH_SHORT).show();
                    cleanTextBox();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ActivityQuestionAddUpgrade.this, R.string.enter_info,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonAttachFile.setOnClickListener(this::fileChooser);

        buttonReset.setOnClickListener(v -> cleanTextBox());
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
            Toast.makeText(ActivityQuestionAddUpgrade.this, R.string.write_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(ActivityQuestionAddUpgrade.this, "No data in file!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Uri fileData = data.getData();
            assert fileData != null;
            File file = new File(fileData.getPath());
            filePath = file.getPath();

            Toast.makeText(ActivityQuestionAddUpgrade.this, "File Path: " + filePath,
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityQuestionAddUpgrade.this, "File not attached!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fileChooser(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] mimeTypes = {"image/*", "audio/*", "video/*", "application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, "Pick a file"), SELECT_FILE);
    }

    private void defineVariables() {
        TextView titleActivity = findViewById(R.id.titleActivity);
        questionText = findViewById(R.id.questionText_upg_quest);
        optionA = findViewById(R.id.optionA_upg_quest);
        optionB = findViewById(R.id.optionB_upg_quest);
        optionC = findViewById(R.id.optionC_upg_quest);
        optionD = findViewById(R.id.optionD_upg_quest);
        optionE = findViewById(R.id.optionE_upg_quest);
        answer = findViewById(R.id.answer_upg_quest);
        buttonAttachFile = findViewById(R.id.attach_file_upg_quest);
        buttonOK = findViewById(R.id.attach_file_ok_upg);
        buttonReset = findViewById(R.id.buttonReset_upg);
        editTexts.add(questionText);
        editTexts.add(optionA);
        editTexts.add(optionB);
        editTexts.add(optionC);
        editTexts.add(optionD);
        editTexts.add(optionE);
        editTexts.add(answer);

        questions = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Questions")) {
                questions = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Questions");
            }
            else{
                questions = Question.getQuestions();
            }
        }

        // fromWhichActivity is the string value of activity's TextView title, which is obtained by
        // intent according to sender activity. The same java and xm files are used for both
        // Add Question and Update Question activities
        fromWhichActivity = Objects.requireNonNull(getIntent().getExtras()).getString("Title", " ");
        if(fromWhichActivity.equals("Upgrade Question")){
            fill();
        }
        titleActivity.setText(fromWhichActivity);
    }

    private void cleanTextBox(){
        for(EditText i : editTexts){
            i.setText("");
        }
    }

    private boolean isFilled(){
        String str;
        for(EditText i : editTexts){
            str = i.getText().toString();
            if(TextUtils.isEmpty(str)){
                return false;
            }
        }
        return true;
    }
    
    private void fill(){
        questionNo = Objects.requireNonNull(getIntent().getExtras()).getInt("Question No", 0);
        questionText.setText(questions.get(questionNo).getQuestion());
        optionA.setText(questions.get(questionNo).getOptionA());
        optionB.setText(questions.get(questionNo).getOptionB());
        optionC.setText(questions.get(questionNo).getOptionC());
        optionD.setText(questions.get(questionNo).getOptionD());
        optionE.setText(questions.get(questionNo).getOptionE());
        answer.setText(questions.get(questionNo).getAnswer());
        optionA.setText(questions.get(questionNo).getOptionA());
        filePath = questions.get(questionNo).getFilePath();
    }
}
