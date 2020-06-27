package com.example.pankajnotereminder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface embedDao {

    @Insert
    void Insert(Notes notes);

    @Update
    void Update(Notes notes);

    @Delete
    void Delete(Notes notes);

    @Query("DELETE FROM table_list")
    void deleteAllNotes();


    @Query("SELECT * FROM TABLE_LIST ORDER BY priority DESC")
    LiveData<List<Notes>>getAllNotes();

    //will be notified immediately idf changes made
}
