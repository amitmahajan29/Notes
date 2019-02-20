package acer.example.com.notesexample;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity
{
    SharedPreferences preferences; //Since we use SHaredPreferences concept to save settings, we created this object.
                                    // What is stored in this object is accessible in whole project.

    SharedPreferences.Editor editor; //This object of Editor class will be used to modify the sahred preferences.
    Switch swRead;
    TextView tvMax;
    SeekBar sbMax;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String str = getIntent().getExtras().getString("msg"); //We had passed a string so we received it in a string.
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        //Initialising objects. And for sharedPreferences we used its key(String) stored in other java file by <ClassName>.<keyName> as below.
        preferences = getSharedPreferences(Prefs.NOTES_SETTINGS,MODE_PRIVATE);
        swRead = findViewById(R.id.sw1);
        tvMax = findViewById(R.id.tv1);
        sbMax = findViewById(R.id.sb1);

        //For accessing the previous(setting the default given values if launched first time) values of settings.
        Boolean readOnlyMode = preferences.getBoolean(Prefs.READ_ONLY,false);
        swRead.setChecked(readOnlyMode);

        int maxCount = preferences.getInt(Prefs.MAX_NOTES,10);
        sbMax.setProgress(maxCount);

        tvMax.setText("Maximum notes : "+maxCount);

        //Listener for changes of seekbar.
        sbMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                tvMax.setText("Maximum Notes : "+i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {


            }

        });


    }

    public void btn1_Click(View v)
    {
        editor = preferences.edit(); //FOr editing the preferences.Editor object,
        editor.putInt(Prefs.MAX_NOTES, sbMax.getProgress() );
        editor.putBoolean(Prefs.READ_ONLY, swRead.isChecked() );
        editor.apply();
        Toast.makeText(this, "Settings Saved Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }



}