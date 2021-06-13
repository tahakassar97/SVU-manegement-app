package com.tec.svu303_v2.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.svu303_v2.R;
import com.tec.svu303_v2.StaticData;
import com.tec.svu303_v2.UpdateQuestionActivity;
import com.tec.svu303_v2.sqlite_database.models.Question;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final ArrayList<Question> questions;
    private final Context context;
    private final StaticData appData;

    public QuestionAdapter(ArrayList<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
        appData = StaticData.getInstance(context);
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionAdapter.ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.question_card,
                                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.title.setText(question.getTitle());
        holder.grade.setText(String.valueOf(question.getGrade()));
        holder.delete.setOnClickListener(v -> {
            appData.playSound();
            try {
                new AlertDialog.Builder(context)
                        .setMessage("Sure to delete question?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            appData.playSound();
                            Question.deleteQuestion(appData.getDatabaseManager(), question.getId());
                            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                            removeAt(position);
                        })
                        .create()
                        .show();
            } catch (Exception e) {
                Toast.makeText(context, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(v -> {
            appData.playSound();
            Intent intent = new Intent(context, UpdateQuestionActivity.class);
            intent.putExtra("question", question);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView grade;
        ImageView edit;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTV);
            grade = itemView.findViewById(R.id.gradeTV);
            edit = itemView.findViewById(R.id.editIV);
            delete = itemView.findViewById(R.id.deleteIV);

        }
    }

    public void removeAt(int position) {
        questions.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, questions.size());
    }
}
