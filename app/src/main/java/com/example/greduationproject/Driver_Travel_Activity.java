package com.example.greduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greduationproject.login_tasks.models.Driver;
import com.example.greduationproject.login_tasks.models.select_driver_travel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Driver_Travel_Activity extends AppCompatActivity {
    StorageReference storageReference;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseAuth firebaseAuth;
    EditText drivergofrom,drivergoto,edttime,edtdate,edtnumberofpassenger,numberofbags,smoking,priceoftravel;
    Button btn_confirm;
    Driver info;

    Show_map show_map=new Show_map();
    String to=show_map.to;
    public  int year,month,day,hour,min,second;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__travel_);
        storageReference = FirebaseStorage.getInstance().getReference();

        final String  id1=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference2=FirebaseDatabase.getInstance().getReference().child("Driver").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("travel");
        databaseReference = FirebaseDatabase.getInstance().getReference("select_driver_travel").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("travels").child("1234");





        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String to= dataSnapshot.child("to").getValue()+"";
               drivergoto.setText(to);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        drivergofrom = findViewById(R.id.driver_go_from);
        drivergoto = findViewById(R.id.driver_go_to);
        edtdate = findViewById(R.id.driver_date);
        edttime = findViewById(R.id.driver_time);
        edtnumberofpassenger = findViewById(R.id.number_of_passengers);
        numberofbags = findViewById(R.id.number_of_bags_passenger);
        smoking = findViewById(R.id.driver_smoking);
        priceoftravel=findViewById(R.id.travel_price22);
        btn_confirm = findViewById(R.id.driver_confirmation);


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
        priceoftravel.setText("12$");


        /******************************* Confirmation ************************************/
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String from = drivergofrom.getText().toString().trim();
                final String to = drivergoto.getText().toString().trim();
                final String date = edtdate.getText().toString().trim();
                final String time = edttime.getText().toString().trim();
                final String numberbags = numberofbags.getText().toString().trim();
                final String numberofpassenger = edtnumberofpassenger.getText().toString().trim();
                final String smokingornot = smoking.getText().toString().trim();
                final String price=priceoftravel.getText().toString().trim();

               String id = databaseReference.getKey();


                select_driver_travel info = new select_driver_travel(id, from, to, date, time, numberofpassenger, numberbags, smokingornot,price);

                databaseReference.setValue(info);


                /****************************** Test Recycler ********************************/
                if(TextUtils.isEmpty(from)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter the place", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(to)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter the place", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(date)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter the date", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(time)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter the time", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(numberbags)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter number of bage", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(numberofpassenger)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter the number of passenger", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(smokingornot)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter somking or not", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(price)){
                    Toast.makeText(Driver_Travel_Activity.this, "please enter price", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Driver_Travel_Activity.this, Request_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}