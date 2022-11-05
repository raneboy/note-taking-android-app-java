package com.example.db10.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.example.db10.Dao.CategoryDao;
import com.example.db10.Model.Category;
import com.example.db10.Room.NoteRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CategoryRepository {

    private NoteRoomDatabase instance;
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Context context){
        instance = NoteRoomDatabase.getInstance(context);
        categoryDao = instance.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories(){
        return allCategories;
    }

    public void insert(Category category){}

    public void delete(Category category){}

    private static class InsertCategoryAsyncTask extends AsyncTask<Category,Void,Void>{
        CategoryDao categoryDao;
        public InsertCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category,Void,Void>{
        CategoryDao categoryDao;
        public DeleteCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.delete(categories[0]);
            return null;
        }
    }
}
