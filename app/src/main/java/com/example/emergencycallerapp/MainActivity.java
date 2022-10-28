package com.example.emergencycallerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtX;
    EditText txtY;
    EditText txtZ;
    EditText txtmobile;
    AlertDialog.Builder alert1;
    AlertDialog.Builder alert2;
    AlertDialog.Builder alert;


    TextView lblX;
    TextView lblY;
    TextView lblXZ;
    SensorManager sensor;
    List sensorList;
    Intent callIntent;
    SensorEventListener senListener;
    Button btnAdd;
    Button btnReset;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       txtX= this.findViewById(R.id.txtX);
       txtY = this.findViewById(R.id.txtY);
       txtZ = this.findViewById(R.id.txtZ);
       txtmobile = this.findViewById(R.id.txtMobile);
       btnAdd  = this.findViewById(R.id.btnAdd);
       btnReset = this.findViewById(R.id.btnReset);


        alert1 = new AlertDialog.Builder(this);
        alert = new AlertDialog.Builder(this);
        sensor = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorList = sensor.getSensorList(Sensor.TYPE_ACCELEROMETER);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  number = txtmobile.getText().toString();
                  if(number.isEmpty()){
                      alert1.setMessage("Please enter a Emergency Number..");
                      alert1.setPositiveButton("ok",null);
                      alert1.show();
                      btnAdd.setEnabled(true);
                      btnReset.setEnabled(false);
                  }else{
                      btnReset.setEnabled(true);
                      btnAdd.setEnabled(false);
                      txtmobile.setEnabled(false);


                  }




            }
        });
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                txtmobile.setText("");
                number=null;

                    btnAdd.setEnabled(true);
                    btnReset.setEnabled(false);
                    txtmobile.setEnabled(true);
            }

        });


            senListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {

                    float[] value = sensorEvent.values;
                    txtX.setText(Double.toString(Math.round(value[0])));
                    txtY.setText(Double.toString(Math.round(value[1])));
                    txtZ.setText(Double.toString(Math.round(value[2])));

                    if((value[2] > 15)&& number!=null){
                         alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setMessage("Please Enter valid Number");
                        alert.show();

                        callIntent = new Intent();
                        callIntent.setAction(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+ number));
                        startActivity(callIntent);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}
            };
        sensor.registerListener(senListener,(Sensor) sensorList.get(0),SensorManager.SENSOR_DELAY_NORMAL);

    }
}