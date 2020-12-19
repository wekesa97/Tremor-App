package com.simplisticwriting.tremordetectorapplication;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String FileName = "myFile";
    Button BtnSave, BtnRead;
    int countdown=0;
    EditText editName;
    TextView readName;
    String name;
    public String phonenumber;
    DataBaseHelper myDb;
    String dateTime;
    EditText txtName,txtSurName,txtMarks;
    Button btnClick;
    Button btnActivity1;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnSave =  findViewById(R.id.idBtnSave);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        myDb=new DataBaseHelper(this);
        txtName=findViewById(R.id.idName);
        btnClick=findViewById(R.id.idBtn);
        btnActivity1=findViewById(R.id.idBtnActivity);
        Button btnActivity2=findViewById(R.id.idBtnActivity2);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        BtnRead =  findViewById(R.id.idBtnRead);
        BtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });
        editName =  findViewById(R.id.idName);
        readName =  findViewById(R.id.idReadtxt);
    }
    private void readFile() {
        SharedPreferences sharedPref = getSharedPreferences(FileName,Context.MODE_PRIVATE);
        String defaultValue = "DefaultName";
        String shared_phone_number = sharedPref.getString("shared_phone_number", defaultValue);
        readName.setText(shared_phone_number);
        Toast.makeText(this,"Data :"+shared_phone_number,Toast.LENGTH_SHORT).show();
        phonenumber=shared_phone_number;
    }
    private void saveFile() {
        String strName = editName.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences(FileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("shared_phone_number", strName);
        editor.commit();
        Toast.makeText(this,"Data Saved Successfully"+name,Toast.LENGTH_SHORT).show();
    }
    public void btnClickAct1(View v){
        Intent i = new Intent(this,Main2Activity.class);
        startActivity(i);
    }
    public void btnClickAct2(View v){
        Intent i = new Intent(this,SensorsActivity.class);
        startActivity(i);
    }
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            //mAccelCurrent will be close to one when there is no movement
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            //linear_acceleration = acceleration - acceleration due to gravity
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (mAccel > 12) {
                countdown++;
                if(countdown<=4){

                    Toast.makeText(getApplicationContext(),"countdown is"+countdown,Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
                    SmsManager sms = SmsManager.getDefault();
                    Calendar calender = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm a ");
                    String dateTime = simpleDateFormat.format(calender.getTime());
                    String TremorEvent = "Tremor at "+dateTime+"";
                    Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                    String event_data=TremorEvent;
                    Boolean result=myDb.insertData(event_data);
                    if(result==true){
                        Toast.makeText(getApplicationContext(),"Data Inserted Successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Data Insertion Failed",Toast.LENGTH_SHORT).show();
                    }
                    try{
                        sms.sendTextMessage(phonenumber,null,TremorEvent,pi,null);
                    }catch (Exception e){

                    }
                    countdown=0;
                }

            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}





