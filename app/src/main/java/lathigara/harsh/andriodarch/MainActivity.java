package lathigara.harsh.andriodarch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NEW_REQUEST = 1;
    public static final int EDIT_NEW_REQUEST = 2;
    // we created note class and entity for table
    // our repository will usr note dao to retive all entries from our table
    // and it will be connceted with live data to repository
    // same way viewmodel gets that from repository via livedata
    // main activity not stores data but it observes data via live data

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton floatingActionButton = findViewById(R.id.addNote);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NEW_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recylcer_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final Noteadapter noteadapter = new Noteadapter();
        recyclerView.setAdapter(noteadapter);

        // we will only have ref to our viewmodel in our  activity
        //because we are providing singleton insance we can not provide concrete implementation
        noteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteViewModel.class);
        // here we are  calling our livedatafrom repository to viewmodel and viewmdoel to activty
        // we have to provide  lifecycler owenr and observer for this data becaue this getAllNote metoh is observable for observers meand it will emit all live data
        // and live data is aware pf lifecycle that means our live data will have eye on our lifecycler metohds
        noteViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                //that way it will provides us chnages from our database if any  chnages happens
                // this here we willupdate our recycler view
                Toast.makeText(MainActivity.this, "On changed", Toast.LENGTH_LONG).show();
                noteadapter.setNotes(notes);


            }
        });

        //swipe toDelete                                                 // directions
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteadapter.getNoteAt(viewHolder.getAdapterPosition()));


            }
        }).attachToRecyclerView(recyclerView);

        noteadapter.setOnItemClickListner(new Noteadapter.OnItemClickLisner() {
            @Override
            public void onItemClick(Notes note) {
                // here we are passing our data to addedit activity to update our data
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESC, note.getDesc());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRY, note.getPriority());
                startActivityForResult(intent, EDIT_NEW_REQUEST);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.EXTRA_DESC);
            int pry = data.getIntExtra(AddEditNoteActivity.EXTRA_PRY, 1);
            Notes note = new Notes(title, desc, pry);
            noteViewModel.insert(note);
        } else if (requestCode == EDIT_NEW_REQUEST && resultCode == RESULT_OK) {
            int id  = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            if (id == -1){
                Toast.makeText(this, "Note cant be updatad", Toast.LENGTH_LONG).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.EXTRA_DESC);
            int pry = data.getIntExtra(AddEditNoteActivity.EXTRA_PRY, 1);
            Notes notes = new Notes(title,desc,pry);
            notes.setId(id);
            noteViewModel.updater(notes);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNotes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }

    }
}
