package com.dtn.schedulemanagementapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.NewUserActivity;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.models.Category;
import com.dtn.schedulemanagementapp.models.User;

import java.util.List;
import java.util.Random;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private List<Category> categories;

    Context context;

    public CategoryAdapter(List<Category> categories, Context context){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.lblCateName.setText(category.getName());
        category.setColor(generateRandomColor()+"");

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//
//                        else if (item.getItemId() == R.id.iDelete) {
//
//                            String username = String.valueOf(holder.lblUsername.getText());
//                            DBHelper dbHelper = DBHelper.getInstance(v.getContext());
//                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                            builder.setTitle("Delete")
//                                    .setMessage("Are you sure to delete this user.\nAll schedules, categories of this user also be deleted")
//                                    .setIcon(R.drawable.trash_solid)
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if(dbHelper.deleteUser(username) != 0) {
//                                                deleteItem(position);
//                                                Toast.makeText(v.getContext(), "Delete user " + username + " successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                            else Toast.makeText(v.getContext(), "Something has wrong", Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//                                    })
//                                    .create().show();
//
//
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCate;
        private TextView lblCateName;
        private ImageButton categoryAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCate = itemView.findViewById(R.id.imgCate);
            lblCateName = itemView.findViewById(R.id.lblCateName);
            categoryAction = itemView.findViewById(R.id.categoryAction);

            imgCate.setBackgroundColor(generateRandomColor());

            categoryAction.setOnClickListener(v ->  {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.user_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.iEdit) {

                        DBHelper dbHelper = DBHelper.getInstance(v.getContext());
                        Category category = dbHelper.getCategoryByName(lblCateName.getText().toString().trim());

                        //build alertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        EditText taskEditText = new EditText(v.getContext());
                       builder.setTitle("Edit category name")
                                .setMessage("Enter your new category name: ")
                                .setView(taskEditText)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String test = taskEditText.getText().toString().trim();
                                    }
                                })
                                .setNegativeButton("Cancel", null);
                        builder.create().show();
                         if(dbHelper.updateCategory(category))
                             Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                         else
                             Toast.makeText(v.getContext(), "Update error", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(v.getContext(), NewUserActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("selected_category", ca);
//
//                        intent.putExtras(bundle);
//                        v.getContext().startActivity(intent);

                        return true;
                    }
                    return true;
                });
            });
        }
    }
    public static int generateRandomColor() {
        Random random = new Random();

        int red = 30 + random.nextInt(200);
        int green = 30 + random.nextInt(200);
        int blue = 30 + random.nextInt(200);
        return Color.rgb(red, green, blue);
    }
}
