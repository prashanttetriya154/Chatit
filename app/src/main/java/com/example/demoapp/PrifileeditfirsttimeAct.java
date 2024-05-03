package com.example.demoapp;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class PrifileeditfirsttimeAct extends AppCompatActivity {
    CardView cardView;
    Button imageedit,savebtn;
    TextView pleasewait;
    EditText nameeditext,aboutedittext;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DocumentReference documentReference;
    FirebaseStorage storage;
    ShapeableImageView imageView;
    Uri selectedImage;
    String currentuser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prifileeditfirsttime);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cardView=findViewById(R.id.cardview);
        imageedit=findViewById(R.id.imageeditbtn);
        savebtn =findViewById(R.id.savedetails);
        pleasewait=findViewById(R.id.pleasewait);
        imageView =findViewById(R.id.dpimage);
        progressBar=findViewById(R.id.progressbar);
        nameeditext=findViewById(R.id.usernameET);
        aboutedittext=findViewById(R.id.aboutET);
        database= FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        currentuser=auth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        getdata();
        // Toast.makeText(ProfileEditeActivity.this,auth.getCurrentUser().getPhoneNumber(),Toast.LENGTH_SHORT).show();
        imageedit.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .cropOval()
                    .maxResultSize(512,512,true)
                    .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                    .createIntentFromDialog((Function1)(new Function1(){
                        public Object invoke(Object var1){
                            this.invoke((Intent)var1);
                            return Unit.INSTANCE;
                        }
                        public final void invoke(@NotNull Intent it){
                            Intrinsics.checkNotNullParameter(it,"it");
                            launcher.launch(it);
                        }
                    }));
        });
        progressBar.setVisibility(View.GONE);
        pleasewait.setVisibility(View.GONE);
        savebtn.setOnClickListener(v -> {
//            Dialog loding= new Dialog(ProfileEditeActivity.this);
//            loding.setContentView(R.layout.loading);
//            loding.show();
                    progressBar.setVisibility(View.VISIBLE);
                    pleasewait.setVisibility(View.VISIBLE);
                    String savename;
                    String saveabout="Hi I am using Chat it";
                    savename=nameeditext.getText().toString().trim();
                    saveabout=aboutedittext.getText().toString().trim();
                    if (savename.isEmpty()){
                        nameeditext.setError("User name not be empty");
                    }
                    if (selectedImage!=null){
                        StorageReference reference= storage.getReference().child("Profiles").child(savename);
                        String finalSaveabout = saveabout;
                        reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageuri=uri.toString();
                                            String uid= auth.getUid();
                                            String name=nameeditext.getText().toString().trim();
                                            String phone=auth.getCurrentUser().getPhoneNumber();
                                            User user=new User(uid,name,phone,imageuri, finalSaveabout);
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            database.getReference()
                                            .child("Users")
                                            .child(uid)
                                            .setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressBar.setVisibility(View.GONE);
                                                    pleasewait.setVisibility(View.GONE);
                                                    Dialog dialog= new Dialog(PrifileeditfirsttimeAct.this);
                                                    dialog.setContentView(R.layout.onsuccess);
                                                    TextView discription = dialog.findViewById(R.id.dialogdescription);
                                                    discription.setText("");
                                                    Button okbtn=dialog.findViewById(R.id.dialogbtn);
                                                    okbtn.setOnClickListener(v1 -> {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(PrifileeditfirsttimeAct.this,TabbedActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    });
                                                    dialog.show();
                                                }
                                            })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressBar.setVisibility(View.GONE);
                                                            pleasewait.setVisibility(View.GONE);
                                                            Dialog dialog= new Dialog(PrifileeditfirsttimeAct.this);
                                                            dialog.setContentView(R.layout.onsuccess);
                                                            TextView discription = dialog.findViewById(R.id.dialogdescription);
                                                            discription.setText("Something went wrong");
                                                            Button okbtn=dialog.findViewById(R.id.dialogbtn);
                                                            okbtn.setOnClickListener(v1 -> {
                                                                dialog.dismiss();
                                                            });
                                                            dialog.show();
                                                        }
                                                    });
                                        }
                                    });
                                }
                            }
                        });
                    }
                    if (selectedImage==null){
                        String finalSaveabout = saveabout;
                        String imageuri="";
                        String uid= auth.getUid();
                        String name=nameeditext.getText().toString().trim();
                        String phone=auth.getCurrentUser().getPhoneNumber();
                        User user=new User(uid,name,phone,imageuri, finalSaveabout);
                        database.getReference()
                                .child("Users")
                                .child(uid)
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressBar.setVisibility(View.GONE);
                                        pleasewait.setVisibility(View.GONE);
                                        Dialog dialog= new Dialog(PrifileeditfirsttimeAct.this);
                                        dialog.setContentView(R.layout.onsuccess);
                                        TextView discription = dialog.findViewById(R.id.dialogdescription);
                                        discription.setText("");
                                        Button okbtn=dialog.findViewById(R.id.dialogbtn);
                                        okbtn.setOnClickListener(v1 -> {
                                            dialog.dismiss();
                                            Intent intent = new Intent(PrifileeditfirsttimeAct.this,TabbedActivity.class);
                                            startActivity(intent);
                                            finish();
                                        });
                                        dialog.show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.GONE);
                                        pleasewait.setVisibility(View.GONE);
                                        Dialog dialog= new Dialog(PrifileeditfirsttimeAct.this);
                                        dialog.setContentView(R.layout.onsuccess);
                                        TextView discription = dialog.findViewById(R.id.dialogdescription);
                                        discription.setText("Something went wrong");
                                        Button okbtn=dialog.findViewById(R.id.dialogbtn);
                                        okbtn.setOnClickListener(v1 -> {
                                            dialog.dismiss();
                                        });
                                        dialog.show();
                                    }
                                });
                    }
        }
        );
    }
    private void getdata() {
        databaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name=snapshot.child("name").getValue().toString();
                    String about=snapshot.child("about").getValue().toString();
                    nameeditext.setText(name);
                    aboutedittext.setText(about);
                    String image=snapshot.child("profileImage").getValue().toString();
                    if (image.isEmpty()){
                        imageView.setImageResource(R.drawable.profilelogo);
                    }
                    else {
                        Picasso.get().load(image).into(imageView);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null){
//            if (data.getData() !=null){
//                imageView.setImageURI(data.getData());
//                selectedImage=data.getData();
//            }
//        }
//    }
    ActivityResultLauncher<Intent> launcher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                if(result.getResultCode()==RESULT_OK){
                    Uri uri=result.getData().getData();
                    // Use the uri to load the image
                    imageView.setImageURI(uri);
                    selectedImage=uri;

                }else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    ImagePicker.Companion.getError(result.getData());
                }
            });
}