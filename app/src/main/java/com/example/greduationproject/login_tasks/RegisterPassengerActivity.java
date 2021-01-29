package com.example.greduationproject.login_tasks;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;

import com.example.greduationproject.Passenger_Travel_Activity;
import com.example.greduationproject.Show_map;
import com.example.greduationproject.login_tasks.models.Driver;
import com.example.greduationproject.login_tasks.models.users;
import com.example.greduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class RegisterPassengerActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CODE1 = 100;
    static final int REQUEST_IMAGE_CAPTURE1 = 2;
    Button button_confirm_passenger, button1, btn_facebook;
    EditText txt_fullname, txt_email, txt_phone, txt_password, txt_confirmpass, txt_city, address;
    TextView txt_passenger_photo, txt_passenger_idphoto;
    RadioButton radio_male, radio_fmale;
    ImageView personalphoto, idphoto;
    String[] item = {"upload from Galary", "Camera"};
    String[] items1 = {""};
    users information1;
    Uri uriprofile;
    StorageReference storageReference;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;


    Spinner spinner;
    String[] citys = { ".......", "Cairo", "Benha", "Qewisna "};
    String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        findByID();

        confirmBtn();

        imgAction();

        spinner();

    }

    private void spinner() {
        spinner=findViewById(R.id.edt_City_user);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,citys);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                   city=citys[1];
                }
                else if(i==2)
                {
                    city=citys[2];
                }
                else if(i==3)
                {
                    city=citys[3];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void imgAction() {
       // CAMERAPERMISSION = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        txt_passenger_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterPassengerActivity.this);
                alertDialogBuilder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                showimagechooser();

                                break;
                            case 1:
                                dispatchTakePictureIntent();


                        }

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });
        txt_passenger_idphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterPassengerActivity.this);
                alertDialogBuilder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE1);

                                break;
                            case 1:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE1);
                                }


                        }

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });


    }

    private void confirmBtn() {
        button_confirm_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String fullnameofpassenger= txt_fullname.getText().toString();
                final String email = txt_email.getText().toString();
                final String password = txt_password.getText().toString();
                final String phoneofpassenger = txt_phone.getText().toString();
                String confirm = txt_confirmpass.getText().toString();
                final String cityofpassenger = city;

                String gender = "";


                if (radio_male.isChecked()) {
                    gender = "Male";
                }
                if (radio_fmale.isChecked()) {
                    gender = "Female";
                }
                if (TextUtils.isEmpty(fullnameofpassenger)) {
                    Toast.makeText(RegisterPassengerActivity.this, "please enter your name", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterPassengerActivity.this, "please enter your email", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterPassengerActivity.this, "please enter your password", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(phoneofpassenger)) {
                    Toast.makeText(RegisterPassengerActivity.this, "please enter your phone", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(confirm)) {
                    Toast.makeText(RegisterPassengerActivity.this, "please enter password", Toast.LENGTH_LONG).show();

                }
                if (password != confirm) {
                    Toast.makeText(RegisterPassengerActivity.this, "password is not identically", Toast.LENGTH_LONG).show();
                }
                if (phoneofpassenger.length() != 11) {
                    txt_phone.setError(getString(R.string.input_error_phone_invalid));
                    txt_phone.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    txt_password.setError(getString(R.string.input_error_password_length));
                    txt_password.requestFocus();
                    return;
                }


                information1.setCity_passenger(cityofpassenger);
                information1.setFullname_passenger(fullnameofpassenger);
                information1.setEmail_passenger(email);
                information1.setPhone_passenger(phoneofpassenger);
                information1.setGender_passenger(gender);


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterPassengerActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id1 = firebaseAuth.getCurrentUser().getUid();
//


                                    FirebaseDatabase.getInstance().getReference("users").child(id1)
                                            .setValue(information1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(RegisterPassengerActivity.this, "Succed", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(RegisterPassengerActivity.this, Show_map.class);

                                            startActivity(intent);
                                            finish();



                                        }
                                    });


                                } else {
                                    Toast.makeText(RegisterPassengerActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();


                                }

                                // ...
                            }
                        });

                Log.d("eeeeeeeeeee2", information1.getUrl_imag_personal());


            }


        });
    }

    private void findByID() {

        txt_fullname = findViewById(R.id.edt_name_user);
        txt_email = findViewById(R.id.edt_email_user);
        txt_phone = findViewById(R.id.edt_phone_user);
        txt_password = findViewById(R.id.edt_pass_user);
        txt_confirmpass = findViewById(R.id.edt_confirmpass_user);
        radio_male = findViewById(R.id.rd_male_user);
        radio_fmale = findViewById(R.id.rd_fmale_user);
        button_confirm_passenger = findViewById(R.id.btn_contiue);
        btn_facebook = findViewById(R.id.facebookButton);
        personalphoto=findViewById(R.id.personal_pic_passenger);
        idphoto=findViewById(R.id.id_pic_passenger);
        txt_passenger_photo=findViewById(R.id.txtpassengerphoto);
        txt_passenger_idphoto=findViewById(R.id.txtpassengerphotoid);




        information1 = new users();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                uriprofile = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofile);

                    personalphoto.setImageBitmap(bitmap);
                    uploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                personalphoto.setImageBitmap(bmp);
                uploadImage();
            } else if (requestCode == REQUEST_CODE1) {
                uriprofile = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofile);
                    idphoto.setImageBitmap(bitmap);
                    uploadImageId();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE1) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                idphoto.setImageBitmap(bmp);
                uploadImageId();
            }
        }
    }

    public void showimagechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }

    private void uploadImage() {
        if (uriprofile != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference ref=storageReference.child("users").child(uriprofile.getLastPathSegment());
            /////////////////////////// bt5lenii anzl elsora mn el firebase ll app ////////////////////////////////
            ref.putFile(uriprofile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            information1.setUrl_imag_personal(uri.toString());
                            Log.d("eeeeeeeeeeeeee", uri.toString());

                            Toast.makeText(RegisterPassengerActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });


            /** if (uriprofile != null) {
                 final ProgressDialog progressDialog = new ProgressDialog(this);
                 progressDialog.setTitle("Uploading...");
                 progressDialog.show();

                 StorageReference ref = storageReference.child("images/" + firebaseAuth.getCurrentUser().getUid() + UUID.randomUUID().toString() + GetFileExtension(uriprofile));
                 ref.putFile(uriprofile)
                         .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 progressDialog.dismiss();
                                 //  info1.setPersonalimage(taskSnapshot.getUploadSessionUri().toString());

                                 information1.setUrl_imag_personal(taskSnapshot.getUploadSessionUri().toString());
                                 Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                                 Toast.makeText(RegisterPassengerActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 progressDialog.dismiss();
                                 Toast.makeText(RegisterPassengerActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         })
                         .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                 double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                         .getTotalByteCount());
                                 progressDialog.setMessage("Uploaded " + (int) progress + "%");
                             }
                         });**/

        }
    }


    private void uploadImageId() {

        if (uriprofile != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + firebaseAuth.getCurrentUser().getUid() + UUID.randomUUID().toString() + GetFileExtension(uriprofile));
            ref.putFile(uriprofile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            //  info1.setPersonalimage(taskSnapshot.getUploadSessionUri().toString());

                            information1.setUrl_img_id(taskSnapshot.getUploadSessionUri().toString());
                            Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                            Toast.makeText(RegisterPassengerActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterPassengerActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }







            public String GetFileExtension (Uri uri){

                ContentResolver contentResolver = getContentResolver();

                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

                // Returning the file Extension.
                return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

            }


            private void dispatchTakePictureIntent () {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }


