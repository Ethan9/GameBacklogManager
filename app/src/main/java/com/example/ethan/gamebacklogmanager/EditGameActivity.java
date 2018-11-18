package com.example.ethan.gamebacklogmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class EditGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        //Get the intent used to create this activity
        //Save the Game removed from the intent to an object
        Intent intent = getIntent();
        Game game = intent.getParcelableExtra(MainActivity.EXTRA_GAME);
        final EditText title = findViewById(R.id.titleEditText);
        final EditText platform = findViewById(R.id.platformEditText);
        final EditText notes = findViewById(R.id.notesEditText);
        final Spinner status = findViewById(R.id.statusSpinner);
        //If game is not null, then set all the editText boxes to be correct values
        if(game != null) {
            title.setText(game.getTitle());
            platform.setText(game.getPlatform());
            notes.setText(game.getNotes());
            status.setSelection(game.getStatus());
        }
        //if game is null then create new game, to get new database ID to generate
        else{
            game = new Game("","","", 123, "");
        }

        final long id = game.getId();

        FloatingActionButton editFab = findViewById(R.id.editFab);
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Date is created to save current date for last modified value
                //This date is then formatted to look better and give less info
                Date date = new Date();
                DateFormat dateFormat = DateFormat.getDateInstance();

                //Create a game, and setting the id so that when the editing finishes the correct game is updated
                Game game = new Game(title.getText().toString(), platform.getText().toString(),notes.getText().toString(), status.getSelectedItemPosition(), dateFormat.format(date));
                    game.setId(id);
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.EXTRA_GAME, game);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
            }
        });
    }
}
