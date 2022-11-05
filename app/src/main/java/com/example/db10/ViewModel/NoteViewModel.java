package com.example.db10.ViewModel;

import android.app.Application;

import com.example.db10.Model.Note;
import com.example.db10.Repository.NoteReposiroty;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private NoteReposiroty noteReposiroty;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteReposiroty = new NoteReposiroty(application);
        allNotes = noteReposiroty.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void insert(Note note){ noteReposiroty.insert(note);}

    public void delete(Note note){ noteReposiroty.delete(note);}
}
