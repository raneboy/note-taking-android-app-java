package com.example.db10;

import android.content.Intent;
import android.os.Bundle;

import com.example.db10.Adapter.NoteListAdapter;
import com.example.db10.Listener.OnNoteClickListener;
import com.example.db10.Model.Note;
import com.example.db10.Utlis.MainActionModeCallBack;
import com.example.db10.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener{

    RecyclerView recyclerView;
    NoteListAdapter adapter;
    NoteViewModel noteViewModel;


    public final static String VIEW_NOTE_TITLE = "1";
    public final static String VIEW_NOTE_Image = "2";
    public final static String VIEW_NOTE_CONTENT = "3";

    private ActionMode actionMode;
    private boolean multiChoiceMode = false;
    private int checkedCount = 0;

    FloatingActionButton fab;

    public static final int REQUEST_CREATE_NEW_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new NoteListAdapter(this);
        adapter.setOnNoteClickListener(this);

        recyclerView = findViewById(R.id.recyclerview_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);




        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setAllNotes(notes);
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteIntent = new Intent(MainActivity.this,NewNoteActivity.class);
                startActivityForResult(noteIntent,REQUEST_CREATE_NEW_NOTE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Flow","Get the replyIntent");

        Log.d("Flow",""+resultCode);
        if(requestCode == REQUEST_CREATE_NEW_NOTE && resultCode == RESULT_OK && data != null){
            Log.d("Flow","Get the reply information and create note");

            String title = data.getStringExtra(NewNoteActivity.NEW_NOTE_TITLE);
            String content = data.getStringExtra(NewNoteActivity.NEW_NOTE_CONTENT);
            String imageUriInString = data.getStringExtra(NewNoteActivity.NEW_NOTE_IMAGE);

            long date = new Date().getTime();
            Note note = new Note(title,content,imageUriInString,date,-2);
            noteViewModel.insert(note);
            Log.d("Flow","Note added");
        }

    }

    @Override
    public void onClick(View view, int position) {
        if(actionMode == null){
            Note n = adapter.getAdapterNoteByPosition(position);

            Intent viewIntent = new Intent(MainActivity.this,ViewNoteActivity.class);
            viewIntent.putExtra(VIEW_NOTE_TITLE,n.getTitle());
            viewIntent.putExtra(VIEW_NOTE_Image,n.getImage());
            viewIntent.putExtra(VIEW_NOTE_CONTENT,n.getContent());

            startActivity(viewIntent);

        }else{

            Note n = adapter.getAdapterNoteByPosition(position);
            n.setChecked(!n.isChecked());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onLongClick(View view, int position) {

        Note n = adapter.getAdapterNoteByPosition(position);
        n.setChecked(true);

        checkedCount++;

        multiChoiceMode = true;
        adapter.setMultiChooseMode(true);

        if(actionMode != null){
            return false;
        }

        actionMode = startActionMode(actionModeCallBack);
        fab.setVisibility(View.GONE);
        return true;
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        for(Note note : adapter.getAllNotes()){
            if(note.isChecked()){
                note.setChecked(false);
            }
        }

        multiChoiceMode = false;
        adapter.setMultiChooseMode(false);
        actionMode = null;
        fab.setVisibility(View.VISIBLE);


    }

    public void deleteNotes(){
        for(Note note : adapter.getAllNotes()){
            if(note.isChecked()){
                noteViewModel.delete(note);
            }
        }
    }

    MainActionModeCallBack actionModeCallBack = new MainActionModeCallBack() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_main_action_mode,menu);
            return super.onCreateActionMode(mode, menu);
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.menu_main_action_mode_delete :
                    Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
                    deleteNotes();
                    break;
                case R.id.menu_main_action_mode_share :
                    Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_main_action_mode_close :
                    Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return false;
            }
            mode.finish();
            return super.onActionItemClicked(mode,item);

        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return super.onPrepareActionMode(mode, menu);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            super.onDestroyActionMode(mode);
        }

        @Override
        public void setCount(String checkedCount) {
            super.setCount(checkedCount);

        }
    };


}
