package acer.example.com.notesexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.*;

import acer.example.com.notesexample.database.DBHelper;

public class MainActivity extends AppCompatActivity
{
    FloatingActionButton fab; //We did this so that if user had clicked ReadOnly mode, we need this object to hide the fab button.
    SharedPreferences preferences; //For getting the values of settings.

    List<String> notes;
    List<String> ids;
    ArrayAdapter<String> adapter;
    ListView listview;

    private void listNotes()
    {
        notes = new ArrayList<String>();
        ids = new ArrayList<String>();

        DBHelper dbhelper = new DBHelper(MainActivity.this);
        Cursor cursor = dbhelper.getNotes();
        if(cursor.moveToFirst())
        {
            do
            {
                ids.add(cursor.getString(0));
                notes.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showNoteDialouge(-1);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        preferences = getSharedPreferences(Prefs.NOTES_SETTINGS,MODE_PRIVATE);
        Boolean readOnly = preferences.getBoolean(Prefs.READ_ONLY,false);
        fab.setVisibility( readOnly ? View.GONE : View.VISIBLE);
        listview = findViewById(R.id.lv);
        listNotes();

        if(!readOnly)
        {
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    showNoteDialouge(i);
                    return true;
                }
            });
        }
        else
        {
            listview.setOnItemLongClickListener(null);
        }
    }

    private void showNoteDialouge(final int position)
    {
        if(position==-1) //If new record found (clicked on fab button)
        {
            Intent i = new Intent();
            i.setClass(MainActivity.this,NoteActivity.class);
            startActivity(i);
        }
        else //for existing records
        {
            Intent i = new Intent();
            i.setClass(MainActivity.this,NoteActivity.class);
            i.putExtra("note",notes.get(position));
            i.putExtra("id",ids.get(position));
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) //There was inbuilt settings button, we just linked it with the SettingsActivity.java file.
        {
            Intent i = new Intent();
            i.setClass(MainActivity.this,SettingsActivity.class);
            i.putExtra("msg","Settings Opened");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
