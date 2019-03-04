package com.example.cluderquest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;


public class Home extends AppCompatActivity {

    TextView demoText;
    Button scanBtn,nextBtn;
    IntentIntegrator intentIntegrator;
    String randomStr;

    String hour,min,sec;

    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        demoText = findViewById(R.id.program1Text);
        scanBtn = findViewById(R.id.scan1btn);
        nextBtn = findViewById(R.id.next1btn);


        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("First clue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intentIntegrator = new IntentIntegrator(this);

        Intent i = getIntent();
        hour = i.getStringExtra("hour");
        min = i.getStringExtra("min");
        sec = i.getStringExtra("sec");

        String[] array = new String[]{"q1","q2","q3","q4","q5"};
        randomStr= array[new Random().nextInt(array.length)];

        Log.i("sjkdhfkdjsfh","'asdhalshdlkasjkldhaskkdhaklsshdasldhlkask"+"         "+"         "+"             "+"     "+randomStr);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator.initiateScan();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "result not found", Toast.LENGTH_SHORT).show();
            } else try {
                JSONObject obj = new JSONObject(result.getContents());
                for(Iterator<String> iter = obj.keys(); iter.hasNext();) {

                    final String key = iter.next();

                    if (key.equals(randomStr)){
                        String valueString = obj.getString(String.valueOf(key));
                        demoText.setText(valueString);
                        nextBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(), Clue2.class);
                                i.putExtra("pid",randomStr);
                                i.putExtra("hour",hour);
                                i.putExtra("min",min);
                                i.putExtra("sec",sec);
                                startActivity(i);
                            }
                        });
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "You are in wrong way .!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
