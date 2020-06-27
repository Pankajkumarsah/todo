package com.example.pankajnotereminder.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Notes.class},version = 1)
public abstract class Database extends RoomDatabase {

    //need to turn this class into singleton
    private static Database instance;

    public abstract embedDao todoDao();

    public static synchronized Database getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


   private static  class PopulateDbAsyncTask extends AsyncTask<Void,Void, Void>{

       private embedDao embedDao;
       private PopulateDbAsyncTask(Database db){
           embedDao = db.todoDao();
       }

       @Override
       protected Void doInBackground(Void... voids) {
          embedDao.Insert(new Notes("Visit uncles home", "he is ill these days",2));
           embedDao.Insert(new Notes("Visit uncles home", "he is ill these days",4));
           embedDao.Insert(new Notes("Visit uncles home", "he is ill these days",5));
           return null;
       }
   }

}
