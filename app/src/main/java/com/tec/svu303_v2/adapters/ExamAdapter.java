package com.tec.svu303_v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.svu303_v2.R;
import com.tec.svu303_v2.StaticData;
import com.tec.svu303_v2.sqlite_database.models.Exam;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    Context context;
    ArrayList<Exam> exams;
    StaticData appData;

    public ExamAdapter(Context context, ArrayList<Exam> exams) {
        this.context = context;
        this.exams = exams;
        appData = StaticData.getInstance(context);
    }

    @NonNull
    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExamAdapter.ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.exam_card,
                                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAdapter.ViewHolder holder, int position) {
        Exam exam = exams.get(position);
        holder.testTitle.setText(exam.getTestTitle());
        holder.grade.setText(String.valueOf(exam.getGrad()));
        if (appData.getCurrentUserID() == 0) {
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(exam.getName());
        }
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView testTitle;
        TextView grade;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testTitle = itemView.findViewById(R.id.testTitleTV);
            grade = itemView.findViewById(R.id.gradeTV);
            name = itemView.findViewById(R.id.nameTV);
        }
    }
}
