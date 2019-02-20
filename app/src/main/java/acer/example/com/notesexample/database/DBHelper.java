package acer.example.com.notesexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Acer on 03/03/2018.
 */

public class DBHelper extends SQLiteOpenHelper
{
    public static final String name = "notes.db";
    public static final int version = 1;

    public DBHelper(Context context)
    {
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query = "create table notes(id integer primary key autoincrement, note text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    //If in future we decide to change the database structure in upcoming versions then this thing is executed.
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) //i=previous persion of database and i1=new version of database
    {
        String query = "drop table notes if exists";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public Cursor getNotes() //Cursor is an offline record set.
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from notes";
        return db.rawQuery(query,null);


    }

    public String saveNotes(String note)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues param = new ContentValues();
        param.put("note",note); //First parametre = column name and 2nd parametre = value to be added into column
        db.insert("notes",null,param);
        return "Success!";
    }

    public String updateNotes(String id, String note)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues param = new ContentValues();
        param.put("note",note);
        db.update("notes",param,"id=?", new String[]{id} );
        return "Success!";
    }

    public String deleteNotes(String id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes","id=?",new String[]{id} );
        return "Success!";
    }
}