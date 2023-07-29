package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.CategoryAdapter;
import com.dtn.schedulemanagementapp.database.CategoryController;
import com.dtn.schedulemanagementapp.models.Category;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.user_interface.IOnCategoryItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoriesActivity extends AppCompatActivity {
    SearchView svCate;
    RecyclerView rcvCate;

    FloatingActionButton btnAdd;

    List<Category> cates = new ArrayList<>();

    CategoryController categoryController = new CategoryController(AdminCategoriesActivity.this);

    CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories);

        svCate = findViewById(R.id.svCate);
        rcvCate = findViewById(R.id.rcvCate);
        btnAdd = findViewById(R.id.btnAddCateAd);

        User currUser = new User();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_user")) {
            currUser = (User) intent.getSerializableExtra("selected_user");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Categories of " + currUser.getUsername());

        CategoryController cateController = new CategoryController(AdminCategoriesActivity.this);
        cates = cateController.getCategoriesByOnlyUser(currUser.getUsername());

        categoryAdapter = new CategoryAdapter(cates, AdminCategoriesActivity.this);
        rcvCate.setLayoutManager(new LinearLayoutManager(AdminCategoriesActivity.this));
        rcvCate.setAdapter(categoryAdapter);

        categoryAdapter.SetOnCateItemClickListener(new IOnCategoryItemClickListener() {
            @Override
            public void onCategoryItemClick(Category category) {
                int pos = categoryAdapter.getPosCate(category);
                if (pos != -1) {
                    RecyclerView.ViewHolder viewHolder =  rcvCate.findViewHolderForAdapterPosition(pos);
                    showPopup(viewHolder.itemView, category);
                }
            }
        });

        User finalCurrUser = currUser;
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCategoriesActivity.this);
                //Create editText for dialog
                EditText newName = new EditText(AdminCategoriesActivity.this);
                newName.setHint("Enter your new category name");
                builder.setTitle("Add new category")
                        .setView(newName)
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            Category category = new Category();
                            category.setName(newName.getText().toString().trim());
                            String userName = finalCurrUser.getUsername();
                            category.setUsername(userName);
                            long id = categoryController.addCategory(category);
                            if (id != -1) {
                                category.setId((int)id);
                                categoryAdapter.addItem(category);
                            }
                            Toast.makeText(AdminCategoriesActivity.this, "Add category success", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.create().show();
            }
        });

    }

    private void showPopup(View view, Category category) {
        PopupMenu popupMenu = new PopupMenu(AdminCategoriesActivity.this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        int pos = categoryAdapter.getPosCate(category);

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.iEdit) {
                    EditText taskEditText = new EditText(view.getContext());
                    builder.setTitle("Edit category name")
                            .setMessage("Enter your new category name: ")
                            .setView(taskEditText)
                            .setPositiveButton("OK", (dialog, which) -> {
                                category.setName(taskEditText.getText().toString().trim());
                                if(categoryController.updateCategory(category)) {
                                    categoryAdapter.editItem(category, pos);
                                    Toast.makeText(AdminCategoriesActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(AdminCategoriesActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();

                            })
                            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.create().show();
                    return true;
                } else if (item.getItemId() == R.id.iDelete) {
                    categoryController = new CategoryController(view.getContext());
                    builder.setTitle("Delete category")
                            .setMessage("Are you sure to delete this category?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(categoryController.deleteCategory(category)) {
                                        categoryAdapter.deleteItem(pos);
                                        Toast.makeText(AdminCategoriesActivity.this, "Delete category " + category.getName() + " successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(AdminCategoriesActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create().show();


                    return true;
                }
                return false;
            }

        });
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.user_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCategoriesActivity.this, AdminCategoriesUser.class);
        startActivity(intent);
    }
}