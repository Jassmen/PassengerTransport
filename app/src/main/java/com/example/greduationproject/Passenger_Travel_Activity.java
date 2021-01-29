package com.example.greduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greduationproject.Passenger_Travel_Activity;
import com.example.greduationproject.login_tasks.models.select_passenger_travel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Passenger_Travel_Activity extends AppCompatActivity {

    StorageReference storageReference;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText drivergofrom,drivergoto,edttime,edtdate,edtnumberofpassenger,numberofbags,smoking;
    Button btn_confirm;


    Show_map show_map=new Show_map();
    String to=show_map.to;
    public  int year,month,day,hour,min,second;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__travel_);



        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("select_passenger_travel").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("travelspass").child("travel1");
        firebaseAuth = FirebaseAuth.getInstance();
        drivergofrom = findViewById(R.id.passenger_go_from);
        drivergoto = findViewById(R.id.passenger_go_to);
        edtdate = findViewById(R.id.passenger_date);
        edttime = findViewById(R.id.passenger_time);
        edtnumberofpassenger = findViewById(R.id.number_of_passengers_passenger);
        numberofbags = findViewById(R.id.number_of_bags_passenger_passenger);
        smoking = findViewById(R.id.passenger_smoking);
        btn_confirm = findViewById(R.id.passenger_confirmation);

        /******************************* Set Default Text ******************************************/
        to=getIntent().getStringExtra("To");
        year=getIntent().getIntExtra("Year",1999);
        month=getIntent().getIntExtra("Month",1);
        day=getIntent().getIntExtra("Day",1);
        min=getIntent().getIntExtra("Min",1);
        hour=getIntent().getIntExtra("Hour",1);
        second=getIntent().getIntExtra("Second",1);

        Log.i("JS2",to);
        drivergoto.setText(to);
        edtdate.setText(" ( Date : "+day+"/"+month+"/"+year+")");
        edttime.setText(hour+":"+min+":"+second);
        edtnumberofpassenger.setText("1");
        numberofbags.setText("1");
        smoking.setText("No");


        /**************************** Confermation **************************/
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String from_pass = drivergofrom.getText().toString().trim();
                final String to_pass = drivergoto.getText().toString().trim();
                final String date_pass = edtdate.getText().toString().trim();
                final String time_pass = edttime.getText().toString().trim();
                final String numberbags_pass = numberofbags.getText().toString().trim();
                final String numberofpassenger_pass = edtnumberofpassenger.getText().toString().trim();
                final String smokingornot_pass = smoking.getText().toString().trim();

                String id = databaseReference.push().getKey();
                select_passenger_travel info = new select_passenger_travel(id, from_pass, to_pass, date_pass, time_pass, numberofpassenger_pass, numberbags_pass, smokingornot_pass);
                databaseReference.setValue(info);

                /****************** Test Retrival Data ***********************************/
                if(TextUtils.isEmpty(from_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the place", Toast.LENGTH_SHORT).show();
                    
                }
                if(TextUtils.isEmpty(to_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the place", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(date_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the date", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(time_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the time", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(numberbags_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the number of bags", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(numberofpassenger_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the numberofpassenger ", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(smokingornot_pass)){
                    Toast.makeText(Passenger_Travel_Activity.this, "enter the smokingornot ", Toast.LENGTH_SHORT).show();

                }

                else {
                    Intent intent = new Intent(Passenger_Travel_Activity.this, DetailsofdriverActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

}



