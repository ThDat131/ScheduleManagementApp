package com.dtn.schedulemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dtn.schedulemanagementapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    SQLiteDatabase database;
    DBHelper helper;

    public CategoryController(Context context){
        helper = DBHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Cursor c = database.rawQuery("Select * from " + helper.TABLE_CATEGORY, null);
        while (c.moveToNext()){
            Category category = new Category();
            category.setId(c.getInt(0));
            category.setName(c.getString(1));
            category.setUsername(c.getString(3));
            category.setColor(c.getString(2));
            categories.add(category);
        }
        return categories;
    }

    public Category getCategoryByName(String categoryName){
        Category category = null;

        Cursor c = database.query(helper.TABLE_CATEGORY,
                new String[] {helper.CATEGORY_COL_ID, helper.CATEGORY_COL_NAME,
                        helper.CATEGORY_COL_USERNAME, helper.CATEGORY_COL_COLOR},
                helper.CATEGORY_COL_NAME + "= ?",
                new String[]{categoryName},
                null,null,null);

        if(c.moveToFirst()) {
            category = new Category();
            category.setId(c.getInt(0));
            category.setName(c.getString(1));
            category.setUsername(c.getString(3));
            category.setColor(c.getString(2));
            return category;
        }
        return category;
    }

    public boolean updateCategory(Category category){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CATEGORY_COL_NAME, category.getName());
        return database.update(DBHelper.TABLE_CATEGORY, contentValues,
                DBHelper.CATEGORY_COL_ID + "= ?", new String[]{String.valueOf(category.getId())}) > 0;
    }

    public boolean deleteCategory(Category category) {

        return database.delete(DBHelper.TABLE_CATEGORY,
                DBHelper.CATEGORY_COL_ID + "= ?", new String[]{String.valueOf(category.getId())}) > 0;
    }

    public long addCategory(Category category) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_COL_NAME, category.getName());
        values.put(DBHelper.CATEGORY_COL_USERNAME, category.getUsername());

        return database.insert(DBHelper.TABLE_CATEGORY, null, values);
    }
}
