package com.example.trackfit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PreviousWorkoutRecyclerAdapter extends RecyclerView.Adapter<PreviousWorkoutRecyclerAdapter.MyViewHolder> {
    private ArrayList<Workout> workoutslist;
    private RecyclerViewClickListener listener;

    public PreviousWorkoutRecyclerAdapter(ArrayList<Workout> workoutslist, RecyclerViewClickListener listener) {
        this.workoutslist = workoutslist;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView dateText;
        private TextView durationText;

        public MyViewHolder(final View view) {
            super(view);
            dateText = view.findViewById(R.id.dateText);
            durationText = view.findViewById(R.id.durationText);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PreviousWorkoutRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_workout_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousWorkoutRecyclerAdapter.MyViewHolder holder, int position) {
        String date = workoutslist.get(position).getDate();
        String duration = workoutslist.get(position).getDuration();
        holder.dateText.setText(date);
        holder.durationText.setText(duration);
    }

    @Override
    public int getItemCount() {
        return workoutslist.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
