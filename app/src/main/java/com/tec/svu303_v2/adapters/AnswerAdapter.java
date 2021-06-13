package com.tec.svu303_v2.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.svu303_v2.AnswerManagementActivity;
import com.tec.svu303_v2.R;
import com.tec.svu303_v2.StaticData;
import com.tec.svu303_v2.sqlite_database.models.Answer;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    StaticData appData;
    Context context;
    ArrayList<Answer> answers;

    public AnswerAdapter(Context context, ArrayList<Answer> answers) {
        appData = StaticData.getInstance(context);
        this.context = context;
        this.answers = answers;
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerAdapter.ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.answer_card,
                                parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        Answer answer = answers.get(position);
        holder.titleTV.setText(answer.getTitle());
        if (answer.getStatus() == 0) {
            holder.statusTV.setText("False");
        } else {
            holder.statusTV.setText("True");
        }
        holder.editIV.setOnClickListener(v -> {
            appData.playSound();
            Intent intent = new Intent(context, AnswerManagementActivity.class);
            intent.putExtra("answer", answer);
            context.startActivity(intent);
        });

        holder.deleteIV.setOnClickListener(v -> {
            appData.playSound();
            try {
                new AlertDialog.Builder(context)
                        .setMessage("Sure to delete answer?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            appData.playSound();
                            Answer.deleteAnswer(appData.getDatabaseManager(), answer.getId());
                            removeAt(position);
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        })
                        .create()
                        .show();
            } catch (Exception e) {
                Toast.makeText(context, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView statusTV;
        ImageView editIV;
        ImageView deleteIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.answerTitleTV);
            statusTV = itemView.findViewById(R.id.answerStatusTV);
            editIV = itemView.findViewById(R.id.editIV);
            deleteIV = itemView.findViewById(R.id.deleteIV);
        }
    }

    public void removeAt(int position) {
        answers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, answers.size());
    }
}
