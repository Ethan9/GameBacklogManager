package com.example.ethan.gamebacklogmanager;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

//Game adapter class to take data from game class and make that data work with a recyclerview
public class GameAdapter extends RecyclerView.Adapter<GameViewHolder>{
    private GameClickListener gameClickListener;
    private List<Game> mGames;
    private final Resources mResources;

    public GameAdapter(List<Game> mGames, Resources mResources, GameClickListener gameClickListener) {
        this.mGames = mGames;
        this.mResources = mResources;
        this.gameClickListener = gameClickListener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_card, viewGroup,false);

        return new GameViewHolder(view, gameClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder viewHolder, int i) {
        Game game = mGames.get(i);

        viewHolder.titleTextView.setText(game.getTitle());
        viewHolder.platformTextView.setText(game.getPlatform());
        viewHolder.dateTextView.setText(game.getLastModified());
        viewHolder.statusTextView.setText(mResources.getStringArray(R.array.status_array)[game.getStatus()]);
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public void swapList(List<Game> newList){
        mGames = newList;
        if(newList != null){
            this.notifyDataSetChanged();
        }
    }

    interface GameClickListener {
        public void gameOnClick(int i);

    }
}
