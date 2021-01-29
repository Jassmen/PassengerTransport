package com.example.greduationproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greduationproject.login_tasks.models.Driver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    Driver driver;
    TextView personName,personemail,personage,personphone,personcity;
    // String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ImageView personimage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        personName=findViewById(R.id.person_name);
        personemail=findViewById(R.id.person_email);
        personphone=findViewById(R.id.person_phone);
        personage=findViewById(R.id.person_age);
        personcity=findViewById(R.id.person_city);
        personimage=findViewById(R.id.profile);
        storageReference = FirebaseStorage.getInstance().getReference();





        //String  id1=  FirebaseAuth.getInstance().getUid();

        final String driver_id = getIntent().getStringExtra("driver_id");
        Log.d("oooooooooooo2",driver_id);
        if (driver_id!=null || !driver_id.equals(""))
        {


            databaseReference = FirebaseDatabase.getInstance().getReference("Driver").child(driver_id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String name = dataSnapshot.child("fullname").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String age = dataSnapshot.child("age").getValue().toString();
                        String city = dataSnapshot.child("city").getValue().toString();
                        String image=dataSnapshot.child("url_img").getValue().toString();
                        Picasso.get().load(image).into(personimage);
                        personName.setText("Name: "+name);
                        personemail.setText("Email: "+email);
                        personphone.setText("Phone: "+phone);
                        personage.setText("Age: "+age);
                        personcity.setText("City: " +city);

                    }
                    else {
                        Toast.makeText(ProfileActivity.this,"fail",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }








    }
}
