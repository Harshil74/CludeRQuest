package com.example.cluderquest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class Finish extends AppCompatActivity {

    TextView finishText,resultTime;
    String currntId;
    Button scanBtn;
    IntentIntegrator intentIntegrator;

    String hour,min,sec;
    String finishHour,finshhMinute,finshSecond;

    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        finishText = findViewById(R.id.winingText);
        scanBtn = findViewById(R.id.finshBtn);
        resultTime = findViewById(R.id.resultTime);

        mTopToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("final clue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        currntId= i.getStringExtra("pid");
        hour = i.getStringExtra("hour");
        min = i.getStringExtra("min");
        sec = i.getStringExtra("sec");


        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        finishHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        finshhMinute = String.valueOf(calendar.get(Calendar.MINUTE));
        finshSecond = String.valueOf(calendar.get(Calendar.SECOND));



        intentIntegrator = new IntentIntegrator(this);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "result not found", Toast.LENGTH_SHORT).show();
            }
            else{
                try {

                    JSONObject obj = new JSONObject(result.getContents());
                    final String resulttext = obj.getString("result");
                    if (currntId.equals("q6") || currntId.equals("q7") || currntId.equals("q8") || currntId.equals("q9")){
                        finishText.setText(resulttext);
                        String resultHour = String.valueOf((Integer.parseInt(finishHour) - Integer.parseInt(hour)))+String.valueOf ((Integer.parseInt(finshhMinute) -Integer.parseInt(min))) +String.valueOf ((Integer.parseInt(finshSecond) - Integer.parseInt(sec))) ;
                        resultTime.setText(resultHour);

                    }
                    else{
                        Toast.makeText(this, "Oops!! you are in wrong way", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
