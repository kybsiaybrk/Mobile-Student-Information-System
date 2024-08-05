package com.example.unimobilobs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unimobilobs.R;
import com.example.unimobilobs.data.Day;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {

    private List<Day> dayList;

    public CalenderAdapter(List<Day> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = dayList.get(position);
        holder.dayTextView.setText(day.getDay());

        if (day.getEvent() != null && !day.getEvent().isEmpty()) {
            holder.eventTextView.setText(day.getEvent());
            holder.eventTextView.setVisibility(View.VISIBLE);
        } else {
            holder.eventTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTextView;
        public TextView eventTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            eventTextView = itemView.findViewById(R.id.eventTextView);
        }
    }
}
