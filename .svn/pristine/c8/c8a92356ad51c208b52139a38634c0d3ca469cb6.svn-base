package com.niveus.oen.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.niveus.oen.DataObjects.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adarsh on 08-Sep-17.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "oen.db";
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table scenes " + "(id INT, name VARCHAR, type VARCHAR, color VARCHAR, brightness int)");

        db.execSQL("insert into scenes (id, name, type, color, brightness)" + "values(1, 'Blue', 'preset', '#0000FF', 100);");
        db.execSQL("insert into scenes (id, name, type, color, brightness)" + "values(2, 'Red', 'preset', '#FF0000', 100);");
        db.execSQL("insert into scenes (id, name, type, color, brightness)" + "values(3, 'Yellow', 'preset', '#FFFF00', 100);");
        db.execSQL("insert into scenes (id, name, type, color, brightness)" + "values(4, 'Green', 'preset', '#00FF00', 100);");
        db.execSQL("insert into scenes (id, name, type, color, brightness)" + "values(5, 'Sky', 'preset', '#00FFF7', 100);");
        db.execSQL("insert into scenes (id, name, type, color, brightness)" + "values(6, 'Dim', 'preset', '#FFFFFF', 20);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion) {
            case 1:
        }
    }

    public List getAllScenes(){

        List<Scene> sceneList = new ArrayList<Scene>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;

        try {
            c =  db.rawQuery("select * from scenes", null);

            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {

                Scene scene = new Scene();
                scene.setId(c.getInt(c.getColumnIndex("id")));
                scene.setName(c.getString(c.getColumnIndex("name")));
                scene.setType(c.getString(c.getColumnIndex("type")));
                scene.setColor(c.getString(c.getColumnIndex("color")));
                scene.setBrightness(c.getInt(c.getColumnIndex("brightness")));

                sceneList.add(scene);
                c.moveToNext();

            }
        }
        catch (SQLiteException exception){
            Log.d("DBHelper", exception.toString());
        }
        finally {
            c.close();
            db.close();
        }

        return sceneList;
    }

    public void addFav(Scene fav){

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String sql = "INSERT INTO scenes (name, type, color, brightness) VALUES (?, ?, ?, ?);";
            SQLiteStatement statement = db.compileStatement(sql);
            db.beginTransaction();

            statement.clearBindings();
            statement.bindString(1, fav.getName());
            statement.bindString(2, fav.getType());
            statement.bindString(3, fav.getColor());
            statement.bindDouble(4, fav.getBrightness());
            statement.execute();

            db.setTransactionSuccessful();
            db.endTransaction();
        }
        catch(SQLiteException e){
            Log.d("DBHelper", e.toString());
        }
        finally {
            db.close();
        }

    }
}
