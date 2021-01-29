package com.example.greduationproject.login_tasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.greduationproject.Driver_Travel_Activity;
import com.example.greduationproject.MainActivity;
import com.example.greduationproject.Passenger_Travel_Activity;
import com.example.greduationproject.ProfileActivity;
import com.example.greduationproject.R;
import com.example.greduationproject.Show_map;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editPassword, editEmail;
    private FirebaseAuth mAyth;
    String[] items = {"passenger", "Driver"};



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_activity);


        editEmail = findViewById(R.id.edit_text_email_signIn);
        editPassword = findViewById(R.id.edit_text_password_signIn);

        mAyth = FirebaseAuth.getInstance();

        findViewById(R.id.txtSignUp).setOnClickListener(this);
        findViewById(R.id.button_signIn).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolBarSignIn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("");


    }

    private void userSignIn() {
        final String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editEmail.setError(getString(R.string.input_error_email));
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.input_error_email_invalid));
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError(getString(R.string.input_error_password));
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError(getString(R.string.input_error_password_length));
            editPassword.requestFocus();
            return;
        }

        mAyth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().getCurrentUser().getUid();


                    FirebaseDatabase.getInstance().getReference().child("Driver").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if( dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                            {
                             Intent intent = new Intent(SigninActivity.this, Show_map.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                            }
                           else {
                               Intent intent = new Intent(SigninActivity.this, Show_map.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                           }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSignUp:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SigninActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(SigninActivity.this, RegisterPassengerActivity.class);
                                startActivity(intent);
                                break;

                            case 1:
                                Intent intent1 = new Intent(SigninActivity.this, DriverActivity.class);
                                startActivity(intent1);
                                break;
                        }

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                // startActivity(new Intent(SigninActivity.this, RegisterPassengerActivity.class));
                break;

            case R.id.button_signIn:
                userSignIn();
                break;
        }
    }

}

