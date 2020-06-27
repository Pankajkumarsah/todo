package com.example.pankajnotereminder.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pankajnotereminder.db.Database;
import com.example.pankajnotereminder.db.Notes;
import com.example.pankajnotereminder.db.embedDao;

import java.util.List;

public class Repository {
    private embedDao embedDao;
    private LiveData<List<Notes>> allNotes;

    public Repository(Application application){
        Database Database = com.example.pankajnotereminder.db.Database.getInstance(application);
        embedDao = Database.todoDao();
        allNotes = embedDao.getAllNotes();

    }

    public void insert(Notes notes){
        new InsertNoteAsyncTask(embedDao).execute(notes);

    }

    public void update(Notes notes){
        new UpdateNoteAsyncTask(embedDao).execute(notes);

    }

    public void delete(Notes notes){
        new DeleteNoteAsyncTask(embedDao).execute(notes);

    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(embedDao).execute();

    }

    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Notes, Void,Void>{
        private embedDao embedDao;

        private InsertNoteAsyncTask(embedDao embedDao){
            this.embedDao = embedDao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            embedDao.Insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Notes, Void,Void>{
        private embedDao embedDao;

        private UpdateNoteAsyncTask(embedDao embedDao){
            this.embedDao = embedDao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            embedDao.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Notes, Void,Void>{
        private embedDao embedDao;

        private DeleteNoteAsyncTask(embedDao embedDao){
            this.embedDao = embedDao;
        }
        @Override
        protected Void doInBackground(Notes... notes) {
            embedDao.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void,Void>{
        private com.example.pankajnotereminder.db.embedDao embedDao;

        private DeleteAllNoteAsyncTask(embedDao embedDao){
            this.embedDao = embedDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            embedDao.deleteAllNotes();
            return null;
        }
    }

}
