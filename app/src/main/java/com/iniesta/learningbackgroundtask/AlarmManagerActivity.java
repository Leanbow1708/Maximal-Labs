package com.iniesta.learningbackgroundtask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AlarmManagerActivity extends AppCompatActivity {

    private static final String TAG = "ExampleJobService";

    Button btnStart;
    EditText edtHasstag;
    EditText edtLocation;
    PreferenceHelper helper;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AlarmManagerActivity.this,ShowTweetsActivity.class));



            }
        });

        edtHasstag = findViewById(R.id.editText);
        edtLocation = findViewById(R.id.editText2);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper = new PreferenceHelper(AlarmManagerActivity.this);
                String hash = edtHasstag.getText().toString().replace("#", "");
                String loc = edtLocation.getText().toString();
                loc = loc+",1000km";

                helper.putHashtag(hash);
                helper.putLocation(loc);

                if(!TextUtils.isEmpty(hash) && !TextUtils.isEmpty(loc))
                {
                    startAlert();
                }
            else {
                Toast.makeText(AlarmManagerActivity.this, "Please fill the fields", Toast.LENGTH_SHORT);
                }

            }
        });




    }




    public void startAlert(){



        Intent liveIntent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
        PendingIntent recurring = PendingIntent.getBroadcast(getApplicationContext(), 0, liveIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar updateTime = Calendar.getInstance();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), 15* 60 * 1000, recurring);


        Toast.makeText(AlarmManagerActivity.this, "Starting Service in few seconds",Toast.LENGTH_LONG).show();

        //wakeup and starts service in every 16 minutes.


//        Intent intent = new Intent(this, MyBroadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                this.getApplicationContext(), 234324243, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        Calendar updateTime = Calendar.getInstance();
//
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), 1 * 60 * 1000, pendingIntent);

//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (10 * 1000), pendingIntent);
    }

}
