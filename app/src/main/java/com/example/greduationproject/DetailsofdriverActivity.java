package com.example.greduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greduationproject.login_tasks.models.Driver;
import com.example.greduationproject.login_tasks.models.Request;
import com.example.greduationproject.login_tasks.models.select_driver_travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsofdriverActivity extends AppCompatActivity {

    DatabaseReference databaseReference8 = FirebaseDatabase.getInstance().getReference("select_passenger_travel").
            child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("travelspass").child("travel1");

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Driver> driverList;
    ArrayList<Driver> driverList2=new ArrayList<>();
    List<String> l_from_to = new ArrayList<>();
    List<String> l_from_to0 = new ArrayList<>();


    List<select_driver_travel> selList;
    RatingBar ratingBar;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReference4;
    Button btn_getinfo, btn_request;
    List<String> ids = new ArrayList();

    Double d=0.0;
    //ArrayList<select_driver_travel> driverList1;
    /**
     * DatabaseReference databaseReference;
     * RecyclerView Driverlist;
     * ArrayList<Driver> driverList;
     * Button btn_getinfo;
     **/

    Adapter adapter;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsofdriver);
        /**Driverlist = findViewById(R.id.recyle_detailsofdriver);
         Driverlist.setLayoutManager(new LinearLayoutManager(this));
         driverList = new ArrayList<Driver>();
         btn_getinfo = findViewById(R.id.check_btn);**/

        selList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Driver");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("select_driver_travel");
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("request");
        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        /****************RecyclerView ********************/
        recyclerView = findViewById(R.id.recyle_detailsofdriver);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        driverList = new ArrayList<Driver>();
        btn_getinfo = findViewById(R.id.btnaccept);
        btn_request = findViewById(R.id.requst_btn);
        ratingBar = findViewById(R.id.ratingBar);
        position = getIntent().getIntExtra("position", -1);
        adapter = new Adapter(getApplicationContext(), driverList, selList);
        final Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        final Intent intent1 = new Intent(getApplicationContext(), Request_Activity.class);

        adapter.onItemClick(new Adapter.OnInfoClick() {
            @Override
            public void onClick(int pos) {
                intent.putExtra("driver_id", ids.get(pos));
                startActivity(intent);
            }

            @Override
            public void requestOnClick(final int pos) {


                databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Request request = new Request();
                        request.setName_users(dataSnapshot.child("fullname_passenger").getValue() + "");
                        request.setPhone_users(dataSnapshot.child("phone_passenger").getValue() + "");
                        request.setImage_users(dataSnapshot.child("url_imag_personal").getValue() + "");
                        databaseReference3.child(ids.get(pos)).setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DetailsofdriverActivity.this, "done", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailsofdriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        /**  String r=String.valueOf(ratingBar.getRating());
         double value = Double.parseDouble(r);
         if(value>3) {
         Toast.makeText(DetailsofdriverActivity.this, "good", Toast.LENGTH_LONG).show();
         }
         else {
         Toast.makeText(DetailsofdriverActivity.this, "bad", Toast.LENGTH_LONG).show();

         }**/

        //    driverList1=new ArrayList<select_driver_travel>();

        /****************Bgeeb mn el class el esmo Driver el data  *******************/

        getIdsOFromAndTO();



        //String id = databaseReference.getKey();

    }

    private void getIdsOFromAndTO() {

        databaseReference8.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String from=dataSnapshot.child("from").getValue()+"";
                final String to=dataSnapshot.child("to").getValue()+"";


                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot s:dataSnapshot.getChildren())
                        {
                            String from1=s.child("travels").child("1234").child("from").getValue()+"";
                            String to1=s.child("travels").child("1234").child("to").getValue()+"";


                            if (from.equals(from1)&& to.equals(to1))
                            {


                               l_from_to.add(s.getKey());

                                String from11 = s.child("travels").child("1234").child("from").getValue() + "";
                                String to11 = s.child("travels").child("1234").child("to").getValue() + "";
                                String time = s.child("travels").child("1234").child("time").getValue() + "";
                                String price = s.child("travels").child("1234").child("pricetravel").getValue() + "";

                                select_driver_travel travel = new select_driver_travel();
                                travel.setFrom(from11);
                                travel.setTo(to11);
                                travel.setTime(time);
                                travel.setPricetravel(price);
                                Log.d("eeeeeeeeeeeeeeeee", price);
                                selList.add(travel);

                            }
                        }
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                              /**  for (DataSnapshot f:dataSnapshot.getChildren())
                                {
                                    d+= (Double) f.child("rate").getValue();
                                }
                                Double  final_rate=d/dataSnapshot.getChildrenCount();**/


                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                    l_from_to0.add(dataSnapshot1.getKey());
                                    Driver profile = dataSnapshot1.getValue(Driver.class);


                                    //    select_driver_travel profile1=dataSnapshot1.getValue(select_driver_travel.class);


                                    driverList.add(profile);
                                    Log.d("ttttttttttttt",profile.toString());

                                }
                                Log.d("eeeeeeeeeeeeeee5", driverList.toString());


                                for (int i=0;i<l_from_to.size();i++)
                                {
                                    for (int y=0 ;y<l_from_to0.size();y++)
                                    {
                                        if (l_from_to.get(i).equals(l_from_to0.get(y)))
                                        {
                                            driverList2.add(driverList.get(y));
                                        }
                                        Log.d("ttttttttttt",l_from_to.get(i)+"  "+l_from_to0.get(y));
                                    }

                                }

                                getIds();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                Toast.makeText(DetailsofdriverActivity.this, "Ooops.... something wrong", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getIds() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ids.add(snapshot.getKey());
                    Log.d("oooooooooooo", snapshot.getKey() + "");


                }
                for (String id : ids) {
                    DatabaseReference r_test = FirebaseDatabase.getInstance().getReference().child("select_driver_travel")
                            .child(id).child("travels").child("1234");
                    r_test.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot != null) {
//                                String from = dataSnapshot.child("from").getValue() + "";
//                                String to = dataSnapshot.child("to").getValue() + "";
//                                String time = dataSnapshot.child("time").getValue() + "";
//                                String price = dataSnapshot.child("pricetravel").getValue() + "";
//
//                                select_driver_travel travel = new select_driver_travel();
//                                travel.setFrom(from);
//                                travel.setTo(to);
//                                travel.setTime(time);
//                                travel.setPricetravel(price);
//                                Log.d("eeeeeeeeeeeeeeeee", price);
//                                selList.add(travel);
                                Log.d("yyyyyyyyyyy",driverList2.toString());
                                adapter = new Adapter(getApplicationContext(), driverList2, selList);
                                final Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                final Intent intent1 = new Intent(getApplicationContext(), Request_Activity.class);

                                adapter.onItemClick(new Adapter.OnInfoClick() {
                                    @Override
                                    public void onClick(int pos) {
                                        intent.putExtra("driver_id", ids.get(pos));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void requestOnClick(final int pos) {
                                        databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Request request = new Request();
                                                request.setName_users(dataSnapshot.child("fullname_passenger").getValue() + "");
                                                request.setPhone_users(dataSnapshot.child("phone_passenger").getValue() + "");
                                                request.setImage_users(dataSnapshot.child("url_imag_personal").getValue() + "");
                                                request.setIs_accept(false);
                                                databaseReference3.child(ids.get(pos)).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(DetailsofdriverActivity.this, "done", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(DetailsofdriverActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                });
                                Log.d("eeeeeeeeeeeeeeeee", selList.toString());
                                recyclerView.setAdapter(adapter);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
