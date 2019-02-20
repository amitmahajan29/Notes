package acer.example.com.notesexample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import acer.example.com.notesexample.database.DBHelper;

public class NoteActivity extends /*AppCompat*/Activity
{
    String note,id = "0";
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etNote = findViewById(R.id.etNote);

        if(getIntent().hasExtra("id") )
        {
            id = getIntent().getStringExtra("id");
            Button btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.VISIBLE);
        }
        if(getIntent().hasExtra("note"))
        {
            note = getIntent().getStringExtra("note");
            etNote.setText(note);
        }


    }

    public void btnSave_Click(View v)
    {
        DBHelper dbhelper = new DBHelper(NoteActivity.this);
        String msg;
        if(id.equals("0"))
        {
            msg = dbhelper.saveNotes(etNote.getText().toString());
        }
        else
        {
            msg = dbhelper.updateNotes(id,etNote.getText().toString());
        }
        Toast.makeText(NoteActivity.this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void btnDelete_Click(View v)
    {
        DBHelper dbHelper = new DBHelper(NoteActivity.this);
        dbHelper.deleteNotes(id);
        finish();
    }

    public void btnClose_Click(View v)
    {
        finish();
    }
}
