package com.example.ethan.gamebacklogmanager;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

    private static final int EDIT_REQUEST_CODE = 69;
    private static final int ADD_REQUEST_CODE = 420;

    public static final String EXTRA_GAME = "GAME";
    public static GameDatabase gameDatabase;

    private List<Game> mGames = new ArrayList<>();
    private GameAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create final recyclerview so it can be used in onClick methods
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        gameDatabase = GameDatabase.getInstance(this);

        //Create a new game adapter that is passed resources so it can be used in gameAdapter as mainactivity is a class of type activity
        //Also passed a gameclicklistener so I can control what happens when a game is clicked
        mAdapter = new GameAdapter(mGames, getResources(), new GameAdapter.GameClickListener() {
            @Override
            public void gameOnClick(int i) {
                Game game = mGames.get(i);
                Intent intent = new Intent(MainActivity.this, EditGameActivity.class);
                intent.putExtra(EXTRA_GAME, game);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

        recyclerView.setAdapter(mAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //GameAsyncTask is called to load all the games from the database on app startup
        GameAsyncTask gameAsyncTask = new GameAsyncTask(TASK_GET_ALL_GAMES);
        gameAsyncTask.execute();

        //Fab to go to editgameactivity on click
        FloatingActionButton fab = findViewById(R.id.mainFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,EditGameActivity.class),ADD_REQUEST_CODE);
            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            //When games are swiped left or right they are deleted from the database
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                Game game = mGames.get(position);
                GameAsyncTask gameAsyncTask = new GameAsyncTask(TASK_DELETE_GAME);
                gameAsyncTask.execute(game);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //This method is called when the edit page finishes
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == ADD_REQUEST_CODE){
                Game game = data.getParcelableExtra(EXTRA_GAME);
                GameAsyncTask gameAsyncTask = new GameAsyncTask(TASK_INSERT_GAME);
                gameAsyncTask.execute(game);
            }
            else if(requestCode == EDIT_REQUEST_CODE) {
                Game game = data.getParcelableExtra(EXTRA_GAME);
                GameAsyncTask gameAsyncTask = new GameAsyncTask(TASK_UPDATE_GAME);
                gameAsyncTask.execute(game);
            }
        }
    }

    //This is called when AsyncTask finishes and is used to fill in list of games from the database, and updates the UI
    public void onGameDbUpdated(List list) {
        mGames = list;
        updateUI();
    }

    //Swaps current list in the adapter with a new list and notifies the adapter
    public void updateUI(){
        mAdapter.swapList(mGames);
    }

    public class GameAsyncTask extends AsyncTask<Game, Void, List> {
        private int taskCode;

        //Taskcode is passed into GameAsyncTask to define what happens to the game database when a task is executed
        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
            }
        //Because a database is accessed this must happen in the background to avoid the program waiting for database results before continuing
            @Override
            protected List doInBackground(Game... games) {
                switch (taskCode) {
                    case TASK_DELETE_GAME:
                        gameDatabase.gameDao().deleteGames(games[0]);
                        break;
                    case TASK_UPDATE_GAME:
                        gameDatabase.gameDao().updateGames(games[0]);
                        break;
                    case TASK_INSERT_GAME:
                        gameDatabase.gameDao().insertGames(games[0]);
                        break;
            }
            return gameDatabase.gameDao().getAllGames();
        }

        //This happens after results come back from the database
        @Override
        protected void onPostExecute(List games){
            super.onPostExecute(games);
            onGameDbUpdated(games);
        }
    }
}
