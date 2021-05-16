package tr.edu.OnurPorsuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityQuestionList extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    ArrayList<Question> questions;
    RecyclerView recyclerViewList;
    QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        defineVariables();
    }

    public void defineVariables(){

        recyclerViewList = findViewById(R.id.recyclerViewList);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewList.setLayoutManager(layoutManager);

        questions = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Questions")) {
                questions = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Questions");
            }
            else{
                Toast.makeText(ActivityQuestionList.this, R.string.read_error_from_menu, Toast.LENGTH_SHORT).show();
            }
        }

        adapter = new QuestionAdapter(this, questions);
        recyclerViewList.setAdapter(adapter);
    }
}