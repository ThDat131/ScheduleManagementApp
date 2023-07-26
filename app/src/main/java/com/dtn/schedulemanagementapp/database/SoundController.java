package com.dtn.schedulemanagementapp.database;

import static com.dtn.schedulemanagementapp.database.DBHelper.TABLE_SOUND;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dtn.schedulemanagementapp.models.Sound;

import java.util.ArrayList;
import java.util.Locale;

public class SoundController {

    SQLiteDatabase database;
    DBHelper helper;

    public SoundController(Context context){
        helper = DBHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }



    public ArrayList<Sound> getSounds() {
        ArrayList<Sound> sounds = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SOUND;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            try {
                int soundID = cursor.getInt(0);
                String soundName = cursor.getString(1);
                String soundURL = cursor.getString(2);
                sounds.add(new Sound(soundID, soundName, soundURL));
            } catch (Exception ex) {
                Log.d("errorLoadSounds", ex.toString());
            }
        }
        return sounds;
    }
}
