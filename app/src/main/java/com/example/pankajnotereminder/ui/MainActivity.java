package com.example.pankajnotereminder.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pankajnotereminder.R;
import com.example.pankajnotereminder.viewmodel.ViewModel;
import com.example.pankajnotereminder.db.AdapterForTodo;
import com.example.pankajnotereminder.db.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    public static final int EDIT_NOTE_REQUEST = 2;  //for edit

    private ViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditDetails.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AdapterForTodo adapter = new AdapterForTodo();
        recyclerView.setAdapter(adapter);





        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                //update Recyclerview
                adapter.setTodo(notes);
//                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "your todo note is deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AdapterForTodo.OnItemClickListener() {
            @Override
            public void onItemClick(Notes notes) {
                Intent intent = new Intent(MainActivity.this, EditDetails.class);
                intent.putExtra(EditDetails.EXTRA_ID, notes.getId());

                intent.putExtra(EditDetails.EXTRA_TITLE, notes.getTitle());
                intent.putExtra(EditDetails.EXTRA_DESCRIPTION, notes.getTitle());
                intent.putExtra(EditDetails.EXTRA_PRIORITY, notes.getTitle());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }


        });
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EditDetails.EXTRA_TITLE);
            String description = data.getStringExtra(EditDetails.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(EditDetails.EXTRA_PRIORITY, 1);

            Notes notes = new Notes(title, description, priority);
            viewModel.insert(notes);
            Toast.makeText(this, "your todo note is saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditDetails.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "your Todo note can't updated ", Toast.LENGTH_SHORT).show();
                return;

            }
            String title = data.getStringExtra(EditDetails.EXTRA_TITLE);
            String description = data.getStringExtra(EditDetails.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(EditDetails.EXTRA_PRIORITY, 1);

            Notes notes = new Notes(title,description,priority);
            notes.setId(id);
            viewModel.update(notes);

            Toast.makeText(this, "note todo updated successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "note todo not saved, try again", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_All_notes:
                viewModel.deleteAllNotes();
                Toast.makeText(this, "All Todo notes deleted", Toast.LENGTH_SHORT).show();
                return true;

                case R.id.YourFeedback:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s = {"Pankaj15sah@gmail.com"};
                i.putExtra(Intent.EXTRA_EMAIL, s);
                i.putExtra(Intent.EXTRA_SUBJECT, "'Write your Subject'");
                i.putExtra(Intent.EXTRA_TEXT, "Your FeedBack");
                i.setType("message/rfc822");
                Intent chooser = Intent.createChooser(i, "Give your Todo Feedback from Gmail");
                startActivity(chooser);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
