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

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.models.Category;
import com.dtn.schedulemanagementapp.user_interface.IOnCategoryItemClickListener;
import com.dtn.schedulemanagementapp.user_interface.IOnUserItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private List<Category> categories;

    Context context;

    private onRecieveCategoryListener listener;

    public IOnCategoryItemClickListener clickListener;

    public void SetOnCateItemClickListener(IOnCategoryItemClickListener listener) {
        this.clickListener = listener;
    }


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
//        holder.categoryActionDropDownMenu.setOnClickListener(view -> listener.onRecieveCategorySuccess(category, position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCategoryItemClick(category);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCate;
        private TextView lblCateName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCate = itemView.findViewById(R.id.imgCate);
            lblCateName = itemView.findViewById(R.id.lblCateName);
            imgCate.setBackgroundColor(generateRandomColor());
        }
    }
    public void setData(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
    }


    public void addItem(Category category) {
        categories.add(category);
        notifyItemInserted(categories.indexOf(category));
    }

    public void editItem(Category category, int position) {
        categories.set(position, category);
        notifyItemChanged(position);
    }

    public static int generateRandomColor() {
        Random random = new Random();

        int red = 30 + random.nextInt(200);
        int green = 30 + random.nextInt(200);
        int blue = 30 + random.nextInt(200);
        return Color.rgb(red, green, blue);
    }

    public void setOnRecieveCategoryListener(onRecieveCategoryListener listener) {
        this.listener = listener;
    }

    public interface onRecieveCategoryListener {
        void onRecieveCategorySuccess(Category category, int pos);
    }

    public int getPosCate(Category category) {
        return this.categories.indexOf(category);
    }

}
