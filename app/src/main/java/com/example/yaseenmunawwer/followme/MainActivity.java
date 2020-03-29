package com.example.yaseenmunawwer.followme;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity  {
    private TextView textView;

    client cl=new client();
    GpsTool gpsTool=new GpsTool();

    private String selectedPath = "/storage/emulated/0/File.txt";
    private String selectedPath2 = "/storage/emulated/0/File2.txt";

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                //String strEditText
             String lat = data.getStringExtra("Lat");
             String longitude = data.getStringExtra("Long");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button button =(Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.connect(selectedPath);
            }
        });
        Button buttonE =(Button)findViewById(R.id.button3);
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.connect(selectedPath2);
            }
        });
        Button button1 =(Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, GpsTool.class);
                startActivityForResult(intent1, 1);
                startActivityForResult(intent1, 1);
                Intent intent = new Intent(MainActivity.this,CompassMain.class);
                MainActivity.this.startActivity(intent);



                //gpsTool.locManager();

            }
        });




    }
}

