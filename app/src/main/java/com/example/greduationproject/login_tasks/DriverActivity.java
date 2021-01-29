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

import com.example.greduationproject.Driver_Travel_Activity;
import com.example.greduationproject.Show_map;
import com.example.greduationproject.login_tasks.models.Driver;
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

public class DriverActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE1 = 2;
    static final int REQUEST_IMAGE_CAPTURE2 = 3;
    static final int REQUEST_IMAGE_CAPTURE3 = 5;
    static final int REQUEST_IMAGE_CAPTURE4 = 6;
    private static final int REQUEST_CODE = 101;
    private static final int REQUEST_CODE1 = 100;
    private static final int REQUEST_CODE2 = 102;
    private static final int REQUEST_CODE3 = 103;
    private static final int REQUEST_CODE4 = 104;

    // ui
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    TextView textView1, textView2, textView3, textView4, textView5;
    Button button, button1, btn_facebook;
    EditText txt_fullname, txt_email, txt_phone, txt_password, txt_confirmpass, txt_city, carname, carmodel, age;
    RadioButton radio_male, radio_fmale;


    // var
    Driver information;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    String[] items = {"upload from Gallary", "Camera"};
    Uri uriprofile;
    String[] CAMERAPERMISSION;


    Spinner spinner;
    String[] citys = { ".......", "Cairo", "Benha", "Qewisna "};
    String City;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_activity);

        findByID();

        confirmBtn();

        imgAction();
        spinner();

    }
    private void spinner() {
        spinner=findViewById(R.id.edt_City);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,citys);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    City=citys[1];
                }
                else if(i==2)
                {
                    City=citys[2];
                }
                else if(i==3)
                {
                    City=citys[3];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void imgAction() {
        CAMERAPERMISSION = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
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
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
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

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE2);
                                break;
                            case 1:

                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE2);
                                }
                        }


                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE3);
                                break;
                            case 1:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE3);
                                }
                        }

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverActivity.this);
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE4);

                                break;
                            case 1:

                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE4);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String fullname = txt_fullname.getText().toString();
                final String email = txt_email.getText().toString();
                final String password = txt_password.getText().toString();
                final String phone = txt_phone.getText().toString();
                String confirm = txt_confirmpass.getText().toString();
                final String city = City;
                final String model = carmodel.getText().toString();
                final String namecar = carname.getText().toString();
                final String driverage = age.getText().toString();
                String gender = "";


                if (radio_male.isChecked()) {
                    gender = "Male";
                }
                if (radio_fmale.isChecked()) {
                    gender = "Female";
                }
                if (TextUtils.isEmpty(fullname)) {
                    Toast.makeText(DriverActivity.this, "please enter your name", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DriverActivity.this, "please enter your email", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(DriverActivity.this, "please enter your password", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(DriverActivity.this, "please enter your phone", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(confirm)) {
                    Toast.makeText(DriverActivity.this, "please enter password", Toast.LENGTH_LONG).show();

                }
                if (password != confirm) {
                    Toast.makeText(DriverActivity.this, "password is not identically", Toast.LENGTH_LONG).show();
                }
                if (phone.length() != 11) {
                    txt_phone.setError(getString(R.string.input_error_phone_invalid));
                    txt_phone.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    txt_password.setError(getString(R.string.input_error_password_length));
                    txt_password.requestFocus();
                    return;
                }

                information.setAge(driverage);
                information.setModel(model);
                information.setCarName(namecar);
                information.setCity(city);
                information.setFullname(fullname);
                information.setEmail(email);
                information.setPhone(phone);
                information.setGender(gender);


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(DriverActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = firebaseAuth.getCurrentUser().getUid();
//                                     information = new Driver(
//                                            fullname,
//                                            email,
//                                            phone,
//                                            Gender, city, namecar, model, driverage
//                                    );


                                    FirebaseDatabase.getInstance().getReference("Driver").child(id)
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(DriverActivity.this, "Succed", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(DriverActivity.this, Show_map.class);
                                            startActivity(intent);
                                            finish();


                                        }
                                    });


                                } else {
                                    Toast.makeText(DriverActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();


                                }

                                // ...
                            }
                        });

                Log.d("eeeeeeeeeee2", information.getUrl_img());


            }


        });
    }

    private void findByID() {
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        textView5 = findViewById(R.id.text5);
        imageView1 = findViewById(R.id.personal_pic1);
        imageView2 = findViewById(R.id.id_pic);
        imageView3 = findViewById(R.id.driving_pic);
        imageView4 = findViewById(R.id.carlicenes_pic);
        imageView5 = findViewById(R.id.criminal_pic);
        txt_fullname = findViewById(R.id.edt_name);
        txt_email = findViewById(R.id.edt_email);
        txt_phone = findViewById(R.id.edt_phone);
        txt_password = findViewById(R.id.edt_pass);
        txt_confirmpass = findViewById(R.id.edt_confirmpass);
        radio_male = findViewById(R.id.rd_male);
        radio_fmale = findViewById(R.id.rd_fmale);
        button = findViewById(R.id.btnSignIn);
        btn_facebook = findViewById(R.id.facebookButton);
        //txt_city = findViewById(R.id.edt_City);
        carname = findViewById(R.id.edt_namecar);
        carmodel = findViewById(R.id.edt_model);
        age = findViewById(R.id.edt_age);


        information = new Driver();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Driver");
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

                    imageView1.setImageBitmap(bitmap);
                    uploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView1.setImageBitmap(bmp);
                uploadImage();
            } else if (requestCode == REQUEST_CODE1) {
                uriprofile = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofile);
                    imageView2.setImageBitmap(bitmap);
                    uploadImageId();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE1) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView2.setImageBitmap(bmp);
                uploadImageId();
            } else if (requestCode == REQUEST_CODE2) {
                uriprofile = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofile);
                    imageView3.setImageBitmap(bitmap);
                    uploadImageDriver();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE2) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView3.setImageBitmap(bmp);
                uploadImageDriver();
            } else if (requestCode == REQUEST_CODE3) {
                uriprofile = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofile);

                    imageView4.setImageBitmap(bitmap);
                    uploadImageCar();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE3) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView4.setImageBitmap(bmp);
                uploadImageCar();

            } else if (requestCode == REQUEST_CODE4) {
                uriprofile = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriprofile);

                    imageView5.setImageBitmap(bitmap);
                    uploadImageCrim();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_IMAGE_CAPTURE4) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView5.setImageBitmap(bmp);
                uploadImageCrim();


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
            final StorageReference ref=storageReference.child("Driver").child(uriprofile.getLastPathSegment());
            /////////////////////////// bt5lenii anzl elsora mn el firebase ll app ////////////////////////////////
            ref.putFile(uriprofile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            information.setUrl_img(uri.toString());
                            Log.d("eeeeeeeeeeeeee", uri.toString());

                            Toast.makeText(DriverActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });

      /**  if (uriprofile != null) {
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

                            information.setUrl_img(taskSnapshot.getUploadSessionUri().toString());
                            Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                            Toast.makeText(DriverActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DriverActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                            information.setUrl_img_id_card(taskSnapshot.getUploadSessionUri().toString());
                            Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                            Toast.makeText(DriverActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DriverActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadImageDriver() {

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

                            information.setUrl_img_lic_driver(taskSnapshot.getUploadSessionUri().toString());
                            Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                            Toast.makeText(DriverActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DriverActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadImageCar() {

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

                            information.setUrl_img_lic_car(taskSnapshot.getUploadSessionUri().toString());
                            Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                            Toast.makeText(DriverActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DriverActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadImageCrim() {

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

                            information.setUrl_img_crim(taskSnapshot.getUploadSessionUri().toString());
                            Log.d("eeeeeeeeeeeeee", taskSnapshot.getUploadSessionUri().toString());

                            Toast.makeText(DriverActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DriverActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void recognizeFace(Bitmap bitmap, final Context context) {
        //تحويل بيتماب الى بيتماب قابل للتعديل
        final Bitmap mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        final Canvas canvas = new Canvas(mBitmap);
        //الحصول على الكود
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector();
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                // انتهى بنجاح
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background);
                for (FirebaseVisionFace face : firebaseVisionFaces) {
                    Rect bounds = face.getBoundingBox();
                    Bitmap btm = getBitmapFromImageView(drawable);
                    canvas.drawBitmap(btm, bounds.exactCenterX() - btm.getHeight() / 2, bounds.exactCenterY() - btm.getWidth() / 2, null);
                    Log.e("beee", bounds.toShortString());
                }
                //imageView.setImageBitmap(mBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // خطا ما وقع
                Toast.makeText(context, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private Bitmap getBitmapFromImageView(Drawable mDrawable) {
        Drawable drawable = mDrawable;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
