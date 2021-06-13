package com.tec.svu303_v2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.svu303_v2.QuestionsManagementActivity;
import com.tec.svu303_v2.R;
import com.tec.svu303_v2.StaticData;
import com.tec.svu303_v2.TestActivity;
import com.tec.svu303_v2.sqlite_database.models.Test;

import java.util.ArrayList;

public class TestTypeAdapter extends RecyclerView.Adapter<TestTypeAdapter.ViewHolder> {
    private ArrayList<Test> tests;
    private Context context;
    private StaticData staticData;

    public TestTypeAdapter(ArrayList<Test> tests, Context context) {
        this.tests = tests;
        this.context = context;
        staticData = StaticData.getInstance(context);
    }

    @NonNull
    @Override
    public TestTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.test_type_card,
                                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestTypeAdapter.ViewHolder holder, int position) {
        Test test = tests.get(position);
        holder.titleTV.setText(test.getTitle());
        if (staticData.getCurrentUserID() == 0) {
            holder.itemView.setOnClickListener(v -> {
                staticData.playSound();
                Intent intent = new Intent(context, QuestionsManagementActivity.class);
                intent.putExtra("testId", test.getId());
                context.startActivity(intent);
            });
        } else {
            holder.editIV.setImageResource(R.drawable.ic_info);
            holder.itemView.setOnClickListener(v -> {
                staticData.playSound();
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("testId", test.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        ImageView editIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            editIV = itemView.findViewById(R.id.editIV);
        }
    }
}
