package com.example.ethan.gamebacklogmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

//The game viewholder is used to hold views relating to games
public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView titleTextView;
    public final TextView platformTextView;
    public final TextView statusTextView;
    public final TextView dateTextView;
    public final GameAdapter.GameClickListener gameClickListener;

    public GameViewHolder(View itemView, GameAdapter.GameClickListener gameClickListener){
        super(itemView);
        itemView.setOnClickListener(this);

        titleTextView = itemView.findViewById(R.id.titleTextView);
        platformTextView = itemView.findViewById(R.id.platformTextView);
        statusTextView = itemView.findViewById(R.id.statusTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        this.gameClickListener = gameClickListener;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        gameClickListener.gameOnClick(position);
    }
}