package com.example.db10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ViewNoteActivity extends AppCompatActivity {

    ImageView imageView;
    TextView title;
    TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        Intent viewIntent = getIntent();

        imageView = findViewById(R.id.view_note_image);
        title = findViewById(R.id.view_note_title);
        content = findViewById(R.id.view_note_content);

        String get = viewIntent.getStringExtra(MainActivity.VIEW_NOTE_Image);
        Log.d("Flow2",get);


        title.setText(viewIntent.getStringExtra(MainActivity.VIEW_NOTE_TITLE));
        imageView.setImageBitmap(BitmapFactory.decodeFile(get));
        content.setText(viewIntent.getStringExtra(MainActivity.VIEW_NOTE_CONTENT));
    }
}
