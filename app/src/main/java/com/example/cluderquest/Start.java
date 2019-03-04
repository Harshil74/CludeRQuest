package com.example.cluderquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Locale;

public class Start extends AppCompatActivity {

    Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Home.class);

                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                final String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                final String minute = String.valueOf(calendar.get(Calendar.MINUTE));
                final String second = String.valueOf(calendar.get(Calendar.SECOND));
                Log.i("sjhdjfksd","aksdhjkashdkjashdjkashdkjsahsdkjsa"+"       "+"   "+"     "+ hour +"     "+ minute+"     "+second);

                i.putExtra("hour",hour);
                i.putExtra("min",minute);
                i.putExtra("sec",second);
                startActivity(i);
            }
        });
    }
}
