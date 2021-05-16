package tr.edu.OnurPorsuk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<Question> questions;

    public QuestionAdapter(Context context, ArrayList<Question> questions){
        this.context = context;
        this.questions = questions;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView questionText;
        TextView optionA, optionB, optionC, optionD, optionE;
        TextView answer;
        ImageButton upgrade;
        ImageButton delete;
        ImageButton showFile;
        ToggleButton showAnswer;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.questionMark);
            questionText = itemView.findViewById(R.id.questionText);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            optionE = itemView.findViewById(R.id.optionE);
            answer = itemView.findViewById(R.id.answer);
            upgrade = itemView.findViewById(R.id.upgradeQuestion);
            delete = itemView.findViewById(R.id.deleteQuestion);
            showFile = itemView.findViewById(R.id.showFile);
            showAnswer = itemView.findViewById(R.id.showAnswer);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.question_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position){
        holder.imageView.setImageResource(R.drawable.icon_question_mark);
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
        holder.delete.setOnClickListener(v -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            alert.setIcon(R.drawable.icon_alert);
            alert.setTitle("Delete Question");
            alert.setMessage(R.string.delete_question);
            alert.setPositiveButton(R.string.yes, (dialog, which) -> {
                questions.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, questions.size());
                Toast.makeText(v.getContext(), R.string.deleted_question,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ActivityMenu.class);
                Bundle bundleListQuestion = new Bundle();
                bundleListQuestion.putParcelableArrayList("All Questions", questions);
                intent.putExtras(bundleListQuestion);
                context.startActivity(intent);
            });
            alert.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel());
            alert.show();
        });

        holder.upgrade.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ActivityQuestionAddUpgrade.class);
            intent.putExtra("Title", "Upgrade Question");
            intent.putExtra("Question No", position);
            context.startActivity(intent);
        });

        holder.showFile.setOnClickListener(v -> {
            String path = questions.get(position).getFilePath();

//            if(path != null){
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                File file = new File(Environment.getExternalStorageDirectory() + path);
//                Uri data = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID +".provider", file);
//                intent.setDataAndType(data,"/*");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
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

