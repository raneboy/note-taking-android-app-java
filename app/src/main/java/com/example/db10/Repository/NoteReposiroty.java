package com.example.db10.Repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.db10.Dao.NoteDao;
import com.example.db10.Model.Note;
import com.example.db10.Room.NoteRoomDatabase;

import java.util.List;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.lifecycle.LiveData;

public class NoteReposiroty {

    NoteRoomDatabase instance;
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteReposiroty(Application application){
        instance = NoteRoomDatabase.getInstance(application);
        noteDao = instance.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


}
