package com.example.yaseenmunawwer.followme;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Yaseen Munawwer on 17-05-2017.
 */


public class CompassMain extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mCompass;
    private TextView mTextView;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    Float prev=null;
    String lat;
    String longitude;
    private String selectedPath = "/storage/emulated/0/File.txt";
    private String selectedPath2 = "/storage/emulated/0/File2.txt";
    //Bundle bundle = getIntent().getExtras();
    client cl=new client();
    int b=0;


    int g=0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                //String strEditText
                lat = data.getStringExtra("Lat");
                longitude = data.getStringExtra("Long");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mTextView = (TextView) findViewById(R.id.textView2);
        mTextView2 = (TextView) findViewById(R.id.textView4);
        mTextView3 = (TextView) findViewById(R.id.textView5);
        mTextView4 = (TextView) findViewById(R.id.textView6);
        final Button button =(Button)findViewById(R.id.button5);
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
        final Button buttonStart = (Button)findViewById(R.id.button);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //undo this
                    Intent intent = new Intent(CompassMain.this, GpsTool.class);
                    startActivityForResult(intent, 1);
                    mTextView3.setText("Latitude" + lat);
                    mTextView4.setText("Longitude" + longitude);
                    File root = new File("/storage/emulated/0/");
                    File textFile = new File(root, "File.txt");
                    File textFile2 = new File(root, "File2.txt");
                    FileOutputStream stream = new FileOutputStream(textFile);
                    FileOutputStream stream2 = new FileOutputStream(textFile2);
                    FileWriter writer = new FileWriter(textFile);
                    FileWriter writer2 = new FileWriter(textFile2);
                    BufferedWriter bw = null;
                    BufferedWriter bw2 = null;
                    bw = new BufferedWriter(writer);
                    bw2 = new BufferedWriter(writer2);
                    if (lat != null && longitude != null) {
                        bw.write(lat + " " + longitude + "\n");
                        bw2.write(lat + " " + longitude + "\n");
                        bw.flush();
                        bw2.flush();
                    }

                    //writer.flush();
                    writer.close();
                    bw.close();
                    stream.close();
                    stream2.close();
                    b=1;
                    //try {   stream.write("text-to-write".getBytes());
                    //    stream = openFileOutput("File.txt", MODE_APPEND | MODE_WORLD_READABLE);
                    // }finally {
                    //     stream.close();


                    //mTextView2.setText("Heading Angle: "+ lat+longitude);

                } catch (IOException ex) {

                }
                buttonStart.setEnabled(false);
            }




        });
        final Button buttonGoal = (Button)findViewById(R.id.button2);
        buttonGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //undo this
                    Intent intent = new Intent(CompassMain.this, GpsTool.class);
                    startActivityForResult(intent, 1);
                    File root = new File("/storage/emulated/0/");
                    File textFile = new File(root, "File.txt");
                    File textFile2 = new File(root, "File2.txt");
                    FileOutputStream stream = new FileOutputStream(textFile,true);
                    FileOutputStream stream2 = new FileOutputStream(textFile2,true);
                    FileWriter writer = new FileWriter(textFile,true);
                    FileWriter writer2 = new FileWriter(textFile2,true);
                    BufferedWriter bw = null;
                    BufferedWriter bw2 = null;
                    bw = new BufferedWriter(writer);
                    bw2 = new BufferedWriter(writer2);
                    if(lat!=null && longitude!=null) {
                        bw.write(lat + " " + longitude + "\n");
                        bw2.write(lat + " " + longitude + "\n");
                        bw.flush();
                        bw2.flush();
                    }

                    //writer.flush();
                    writer.close();
                    bw.close();
                    stream.close();
                    stream2.close();
                    b=0;

                    //try {   stream.write("text-to-write".getBytes());
                    //    stream = openFileOutput("File.txt", MODE_APPEND | MODE_WORLD_READABLE);
                    // }finally {
                    //     stream.close();





                    //mTextView2.setText("Heading Angle: "+ lat+longitude);

                } catch (IOException ex) {

                }

                buttonGoal.setEnabled(false);



            }

        });

    }

    // The following method is required by the SensorEventListener interface;
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    // The following method is required by the SensorEventListener interface;
// Hook this event to process updates;
    public void onSensorChanged(SensorEvent event) {
        float azimuth = Math.round(event.values[0]);
        mTextView.setText("Heading Angle: " + Float.toString(azimuth));
        // The other values provided are:
        //  float pitch = event.values[1];
        //  float roll = event.values[2];
        if (g == 0) {
            prev = azimuth;
            Intent intent = new Intent(CompassMain.this, GpsTool.class);
            startActivityForResult(intent, 1);
            mTextView3.setText("Latitude" + lat);
            mTextView4.setText("Longitude" + longitude);

            g = 1;
        }
        else if (g == 1 && b == 1) {
            if (Math.abs(azimuth - prev) > 75 && Math.abs(azimuth - prev) < 105) {
                Intent intent = new Intent(CompassMain.this, GpsTool.class);
                startActivityForResult(intent, 1);
                mTextView2.setText("Heading Angle: "+azimuth+"  "+ prev);
                prev =azimuth;
                //String temp = Double.toString(lat);

                mTextView3.setText("Latitude" + lat);
                mTextView4.setText("Longitude" + longitude);


                try {
                    File root = new File("/storage/emulated/0/");
                    File textFile = new File(root, "File.txt");
                    FileOutputStream stream = new FileOutputStream(textFile,true);
                    FileWriter writer = new FileWriter(textFile,true);
                    BufferedWriter bw = null;
                    bw = new BufferedWriter(writer);
                    if(lat!=null && longitude!=null) {

                        bw.write(lat + " " + longitude + "\n" );
                        bw.flush();
                    }
                    //writer.flush();
                    writer.close();
                    bw.close();
                    stream.close();
                    //try {   stream.write("text-to-write".getBytes());
                    //    stream = openFileOutput("File.txt", MODE_APPEND | MODE_WORLD_READABLE);
                   // }finally {
                   //     stream.close();



                    //mTextView2.setText("Heading Angle: "+ lat+longitude);

                } catch (IOException ex) {

                }
            }

            }
        }



    @Override
    protected void onPause() {
        // Unregister the listener on the onPause() event to preserve battery life;
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mCompass, SensorManager.SENSOR_DELAY_NORMAL);
    }
}