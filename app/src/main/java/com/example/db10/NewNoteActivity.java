package com.example.db10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private ImageView image_view_new_note;
    private EditText et_new_note_title;
    private EditText et_new_note;

    private String currentPhotoPath;
    private Uri imageUri;

    public static final int REQUEST_TAKE_PHOTO = 11;


    public static final String NEW_NOTE_TITLE = "com.example.db10.extras.title";
    public static final String NEW_NOTE_CONTENT = "com.example.db10.extras.content";
    public static final String NEW_NOTE_IMAGE = "com.example.db10.extras.image";
    public static final String NEW_NOTE_CATEGORY = "com.example.db10.extras.category";

    private boolean saveAlready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Log.d("Flow","Start NewNoteActivity");

        image_view_new_note = findViewById(R.id.image_view_new_note);
        et_new_note_title = findViewById(R.id.et_new_note_title);
        et_new_note = findViewById(R.id.et_new_note);

        saveAlready = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu_new_note_save){
            saveNote();
        }else if(item.getItemId() == R.id.menu_new_note_image) {
            takeImage();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if(!saveAlready){
            saveNote();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if(!saveAlready){
            saveNote();
        }
        super.onDestroy();
    }

    private void editNote() {
    }

    private void saveNote() {

        Intent replyIntent = new Intent();
        Log.d("Flow"," start to save note");

        String content = et_new_note.getText().toString();
        replyIntent.putExtra(NEW_NOTE_CONTENT,content);
        Log.d("Flow","Content when paused activity = " + content );


        String title = et_new_note_title.getText().toString();

        if(currentPhotoPath != null) {
            String imageUriInString = currentPhotoPath;
            Log.d("Flow2","ImageUri = " + imageUriInString);
        }else{
            Log.d("Flow2","ImageUri is null");
            replyIntent.putExtra(NEW_NOTE_IMAGE,(String)null);
        }

        if(TextUtils.isEmpty(title)) {
            Log.d("Flow","title is null");
            title = null;
            replyIntent.putExtra(NEW_NOTE_TITLE, title);
        }else{
            replyIntent.putExtra(NEW_NOTE_TITLE,title);
        }



        if(TextUtils.isEmpty(content)){
            Log.d("Flow"," ReplyIntent fail");
            setResult(RESULT_CANCELED,replyIntent);
        }else{
            setResult(RESULT_OK,replyIntent);
            Log.d("Flow","Finish the replyIntent");
        }

        saveAlready = true;

        Log.d("Flow","saveNote finish");
        finish();
    }

    private void takeImage() {
        Log.d("Flow2","Start to take image");
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null ){

            Log.d("Flow2","Got camera in this app");

            File photoFile = null;
            try{
                photoFile = createImageFile();
                Log.d("Flow2","File created successfully " + photoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this,"com.example.db10",photoFile);
                Log.d("Flow2","Start to create photo uri = " + photoUri);
                imageUri = photoUri;
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                Log.d("Flow2","Start to take photo");
                startActivityForResult(takePhotoIntent,REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        Log.d("Flow",currentPhotoPath + " is created");
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            Log.d("Flow","Image is get");
            Bitmap bitmap = null;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                image_view_new_note.setVisibility(View.VISIBLE);
                image_view_new_note.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
