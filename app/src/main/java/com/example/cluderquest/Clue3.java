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

public class Clue3 extends AppCompatActivity {

    TextView demoText;
    Button scanBtn,nextBtn;
    String previousId;
    IntentIntegrator intentIntegrator;

    String hour,min,sec;

    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue3);

        demoText = findViewById(R.id.program3Text);
        scanBtn = findViewById(R.id.scan3btn);
        nextBtn = findViewById(R.id.next3btn);

        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("third clue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        previousId = i.getStringExtra("pid");
        hour = i.getStringExtra("hour");
        min = i.getStringExtra("min");
        sec = i.getStringExtra("sec");


        intentIntegrator = new IntentIntegrator(this);

        scanBtn.setEnabled(true);
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
                    final String getId = obj.getString("pid");
                    if (getId.equals(previousId)){
                        demoText.setText(obj.getString("program"));
                        scanBtn.setEnabled(false);
                        nextBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(),Finish.class);
//                              i.putExtra("pid", String.valueOf(getId));
                                i.putExtra("pid",previousId);
                                i.putExtra("hour",hour);
                                i.putExtra("min",min);
                                i.putExtra("sec",sec);
                                startActivity(i);
                            }
                        });
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
