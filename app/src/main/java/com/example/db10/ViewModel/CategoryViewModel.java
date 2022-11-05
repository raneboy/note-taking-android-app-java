package com.example.db10.ViewModel;

import android.app.Application;

import com.example.db10.Model.Category;
import com.example.db10.Repository.CategoryRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CategoryViewModel extends AndroidViewModel {

    CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategories = categoryRepository.getAllCategories();
    }

    public void insert(Category category){ categoryRepository.insert(category);}

    public void delete(Category category){ categoryRepository.delete(category);}

}
