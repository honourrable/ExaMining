package tr.edu.OnurPorsuk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class QuestionAdapterExam extends RecyclerView.Adapter<QuestionAdapterExam.MyViewHolder> {

    private final Context context;
    private final ArrayList<Question> questions;
    private final ArrayList<Question> selectedQuestions;

    public QuestionAdapterExam(Context context, ArrayList<Question> questions, ArrayList<Question> selectedQuestions){
        this.context = context;
        this.questions = questions;
        this.selectedQuestions = selectedQuestions;
    }

    public ArrayList<Question> getSelectedQuestions() {
        return selectedQuestions;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView questionText;
        TextView optionA, optionB, optionC, optionD, optionE;
        TextView answer;
        ImageButton showFile;
        ToggleButton showAnswer;
        CheckBox questionChecked;
        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView){
            super(itemView);

            questionText = itemView.findViewById(R.id.questionTextExam);
            optionA = itemView.findViewById(R.id.optionAExam);
            optionB = itemView.findViewById(R.id.optionBExam);
            optionC = itemView.findViewById(R.id.optionCExam);
            optionD = itemView.findViewById(R.id.optionDExam);
            optionE = itemView.findViewById(R.id.optionEExam);
            answer = itemView.findViewById(R.id.answerExam);
            showFile = itemView.findViewById(R.id.showFileExam);
            showAnswer = itemView.findViewById(R.id.showAnswerExam);
            questionChecked = itemView.findViewById(R.id.checkboxQuestion);
            questionChecked.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener listener){
            this.itemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }
        interface ItemClickListener {
            void onItemClick(View v, int pos);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.question_card_exam, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position){

        holder.questionText.setText(questions.get(position).getQuestion());
        holder.optionA.setText(questions.get(position).getOptionA());
        holder.optionB.setText(questions.get(position).getOptionB());
        holder.optionC.setText(questions.get(position).getOptionC());
        holder.optionD.setText(questions.get(position).getOptionD());
        holder.optionE.setText(questions.get(position).getOptionE());
        holder.answer.setText(R.string.show_answer);
        holder.showAnswer.setOnClickListener(v -> {
            boolean on = ((ToggleButton) v).isChecked();
            if(on){
                holder.answer.setText(questions.get(position).getAnswer());
            } else{
                holder.answer.setText(R.string.show_answer);
            }
        });

        Question question = questions.get(position);
        holder.questionChecked.setChecked(question.isSelected());
        holder.setItemClickListener((v, pos) -> {
            CheckBox myCheckBox= (CheckBox) v;
            Question currentQuestion = questions.get(position);

            if(myCheckBox.isChecked()) {
                currentQuestion.setSelected(true);
                selectedQuestions.add(currentQuestion);
            }
            else if(!myCheckBox.isChecked()) {
                currentQuestion.setSelected(false);
                selectedQuestions.remove(currentQuestion);
            }
        });

        holder.showFile.setOnClickListener(v -> {

//            String path = questions.get(position).getFilePath();
//            if(path != null){
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
//                context.startActivity(intent);
//            }
//            else{
//                Toast.makeText(v.getContext(), R.string.no_file_found,
//                        Toast.LENGTH_SHORT).show();
//            }

        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
