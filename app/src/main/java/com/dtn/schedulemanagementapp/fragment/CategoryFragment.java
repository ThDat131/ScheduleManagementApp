package com.dtn.schedulemanagementapp.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.AdminUserActivity;
import com.dtn.schedulemanagementapp.adapter.CategoryAdapter;
import com.dtn.schedulemanagementapp.adapter.UserAdapter;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.models.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

public class CategoryFragment extends Fragment {

    FloatingActionButton fbtnAddCate;

    RecyclerView recyclerViewCatogories;


    public CategoryFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        List<Category> categories = DBHelper.getInstance(this.getContext()).getAllCategories();
        Log.d("TEST", categories.size()+"");
        categories.forEach(c -> Log.d("HELLO", c.toString()));
        recyclerViewCatogories = v.findViewById(R.id.recyclerViewCatogories);
        CategoryAdapter adapter = new CategoryAdapter(categories, this.getContext());
        adapter.notifyDataSetChanged();
        recyclerViewCatogories.setAdapter(adapter);
        recyclerViewCatogories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return v;
    }

}