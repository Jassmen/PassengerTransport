package com.example.greduationproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greduationproject.login_tasks.DriverActivity;
import com.example.greduationproject.login_tasks.RegisterPassengerActivity;
import com.example.greduationproject.login_tasks.SigninActivity;

public class MainActivity extends AppCompatActivity {
    //ui
    Button btn_signin, btn_regester;
    //var
    String[] items = {"passenger", "Driver"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findyid();
        btnConfirmSignin();


    }

    private void btnConfirmSignin() {
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
        btn_regester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, RegisterPassengerActivity.class);
                                startActivity(intent);
                                break;

                            case 1:
                                Intent intent1 = new Intent(MainActivity.this, DriverActivity.class);
                                startActivity(intent1);
                                break;
                        }

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }

    private void findyid() {
        btn_signin = findViewById(R.id.btnSignIn);
        btn_regester = findViewById(R.id.btnRegister);
    }
}
