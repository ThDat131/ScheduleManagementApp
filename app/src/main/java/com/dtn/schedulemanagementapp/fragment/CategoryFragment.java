package com.dtn.schedulemanagementapp.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.graphics.Color;
import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.AdminUserActivity;
import com.dtn.schedulemanagementapp.activity.MainActivity;
import com.dtn.schedulemanagementapp.adapter.CategoryAdapter;
import com.dtn.schedulemanagementapp.adapter.UserAdapter;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.models.Category;
import com.dtn.schedulemanagementapp.user_interface.IOnCategoryItemClickListener;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;
import com.dtn.schedulemanagementapp.database.CategoryController;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    FloatingActionButton fbtnAddCate;

    RecyclerView recyclerViewCatogories;


    public CategoryFragment() {}

    CategoryController categoryController;

    UserController userController;
    CategoryAdapter categoryAdapter;
    List<Category> categories = new ArrayList<>();

    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerViewCatogories = v.findViewById(R.id.recyclerViewCatogories);
        fbtnAddCate = v.findViewById(R.id.btnAddCate);

        SharedPreferences prefs = requireActivity().getSharedPreferences("pref", MODE_PRIVATE);

        categoryController = new CategoryController(v.getContext());
        userController = new UserController(v.getContext());

        String username = prefs.getString("key_username", "admin");
        categories = categoryController.getCategoriesByUser(username);
        Log.d("CATEGORIS ", categories.size() + "");

        categoryAdapter = new CategoryAdapter(categories, this.getContext());

        recyclerViewCatogories.setAdapter(categoryAdapter);
        recyclerViewCatogories.setLayoutManager(new LinearLayoutManager(this.getContext()));


//        categoryAdapter.setOnRecieveCategoryListener((category, pos) -> {
//            this.pos = pos;
//            RecyclerView.ViewHolder viewHolder =  recyclerViewCatogories.findViewHolderForAdapterPosition(pos);
//            showPopup(viewHolder.itemView, category);
//        });

        categoryAdapter.SetOnCateItemClickListener(new IOnCategoryItemClickListener() {
            @Override
            public void onCategoryItemClick(Category category) {
                pos = categoryAdapter.getPosCate(category);
                if (pos != -1) {
                    RecyclerView.ViewHolder viewHolder =  recyclerViewCatogories.findViewHolderForAdapterPosition(pos);
                    showPopup(viewHolder.itemView, category);
                }
            }
        });

//        categoryAdapter.setOnRecieveCategoryListener(new CategoryAdapter.onRecieveCategoryListener() {
//            @Override
//            public void onRecieveCategorySuccess(Category category, int pos) {
//                this.pos = pos;
//                RecyclerView.ViewHolder viewHolder =  recyclerViewCatogories.findViewHolderForAdapterPosition(pos);
//                showPopup(viewHolder.itemView, category);
//            }
//        });


        fbtnAddCate.setOnClickListener(view ->  {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            //Create editText for dialog
            LinearLayout linearLayout = new LinearLayout(this.getContext());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            View view1 = LayoutInflater.from(this.getContext()).inflate(R.layout.custom_create_category_layout, linearLayout);

            EditText editTextName = view1.findViewById(R.id.editTextCategoryName);
            EditText editTextColor = view1.findViewById(R.id.editTextCategoryColor);

            editTextColor.setFocusable(false);

            final int[] selectedColor = new int[1];
            selectedColor[0] = -1;
            editTextColor.setOnClickListener(view2 -> showDialogPickColor(new SelectedColorListener() {
                @Override
                public void onSelectedColor(int color) {
                    if (color != -1) {
                        selectedColor[0] = color;
                        editTextColor.setText("#"+ Integer.toHexString(color));
                    }

                }
            }, selectedColor[0]));


            builder.setTitle("Add new category")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        Category category = new Category();
                        String userName = prefs.getString("key_username", "User name");
                        String name = editTextName.getText().toString().trim();
                        String color = editTextColor.getText().toString().trim();

                        category.setUsername(userName);

                        if (name.isEmpty()) {
                            Toast.makeText(this.getContext(), "Category name can not be empty!!", Toast.LENGTH_SHORT).show();
                        }
                        else if (color.isEmpty()) {
                            Toast.makeText(this.getContext(), "Category color can not be empty!!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            category.setName(name);
                            category.setColor(color);
                            long id = categoryController.addCategory(category);
                            if (id != -1) {
                                category.setId((int) id);
                                categoryAdapter.addItem(category);
                            }
                            Toast.makeText(view.getContext(), "Add category success", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setView(view1);
            builder.create().show();
        });

        return v;
    }

    private void showDialogPickColor(SelectedColorListener listener, int colorSelected) {
       int defaultColor = CategoryAdapter.generateRandomColor();
        if (colorSelected != -1) defaultColor = colorSelected;
        ColorPickerDialogBuilder
                .with(this.getContext())
                .setTitle("Choose color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .initialColor(defaultColor)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        listener.onSelectedColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    interface SelectedColorListener {
        void onSelectedColor(int color);
    }



    private void showPopup(View view, Category category) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
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
                                    categoryAdapter.notifyDataSetChanged();
                                    Toast.makeText(view.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(view.getContext(), "Something has wrong", Toast.LENGTH_SHORT).show();

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
                                        Toast.makeText(view.getContext(), "Delete category " + category.getName() + " successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(view.getContext(), "Something has wrong", Toast.LENGTH_SHORT).show();
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

}