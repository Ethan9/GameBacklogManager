package com.example.ethan.gamebacklogmanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Game.class}, version = 1)
//Game database class to create a database, and to access it
//This class is a singleton to stop more than one instance of this database being created as anyone who uses the app requires only one
public abstract class GameDatabase extends RoomDatabase{
    public abstract GameDao gameDao();
    private static final String DATABASE_NAME = "gameDatabase";
    private static GameDatabase instance;

    public static GameDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, GameDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }
}
