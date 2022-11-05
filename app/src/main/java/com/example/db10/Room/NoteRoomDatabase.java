package com.example.db10.Room;

import android.content.Context;

import com.example.db10.Dao.CategoryDao;
import com.example.db10.Dao.NoteDao;
import com.example.db10.Model.Category;
import com.example.db10.Model.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, Category.class},version = 1,exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "note-database";

    public abstract NoteDao noteDao();
    public abstract CategoryDao categoryDao();

    private static NoteRoomDatabase instance;

    public static NoteRoomDatabase getInstance(final Context context){
        if(instance == null){
            synchronized (NoteRoomDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),NoteRoomDatabase.class
                            ,"note_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return instance;
    }
}
