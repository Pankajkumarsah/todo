package com.example.pankajnotereminder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pankajnotereminder.db.Notes;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Notes>> allNotes;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Notes notes) {
        repository.insert(notes);
    }

    public void update(Notes notes) {
        repository.update(notes);
    }

    public void delete(Notes notes) {
        repository.delete(notes);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }


}
