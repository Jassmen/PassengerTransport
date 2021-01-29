package com.example.greduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.greduationproject.R;
import com.example.greduationproject.login_tasks.models.Driver;
import com.example.greduationproject.login_tasks.models.Request;
import com.example.greduationproject.login_tasks.models.users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Request_Activity extends AppCompatActivity {
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<Request> usersrequest;
    AdapterforRequest adapterforRequest;
    List<String>ids=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("request").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        recyclerView = findViewById(R.id.recycleforrequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        usersrequest=new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for ( DataSnapshot s:dataSnapshot.getChildren())
                {
                    Request r=new Request();
                    r.setImage_users(s.child("image_users").getValue()+"");
                    r.setName_users(s.child("name_users").getValue()+"");
                    r.setPhone_users(s.child("phone_users").getValue()+"");
                    ids.add(s.getKey());

                    usersrequest.add(r);
                }

                adapterforRequest=new AdapterforRequest(getApplicationContext(),usersrequest);
                adapterforRequest.onClickAccept(new AdapterforRequest.OnAcceptclickList() {
                    @Override
                    public void onClickRequest(int pos) {

                        Request r=new Request();
                        r.setName_users(usersrequest.get(pos).getName_users());
                        r.setImage_users(usersrequest.get(pos).getImage_users());
                        r.setPhone_users(usersrequest.get(pos).getPhone_users());
                        r.setIs_accept(true);

                        databaseReference.child(ids.get(pos)).setValue(r);

                        Toast.makeText(Request_Activity.this, "accept...", Toast.LENGTH_SHORT).show();

                    }
                });
                recyclerView.setAdapter(adapterforRequest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}