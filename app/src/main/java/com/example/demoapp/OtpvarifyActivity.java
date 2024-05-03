package com.example.demoapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpvarifyActivity extends AppCompatActivity {
Button button;
EditText editText;
TextView textView;
String verivicationid,phonenumber;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpvarify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText=findViewById(R.id.editTextNumber2);
        editText.requestFocus();
        textView=findViewById(R.id.textView4);
        button=findViewById(R.id.button);
        verivicationid=getIntent().getStringExtra("id");
        phonenumber=getIntent().getStringExtra("phone");
        textView.setText("+91 "+phonenumber);
        button.setOnClickListener(v -> {
            String otp = editText.getText().toString().trim();
            if (otp.isEmpty()){
                Dialog dialog = new Dialog(OtpvarifyActivity.this);
                dialog.setContentView(R.layout.enterphonenumber);
                TextView discription = dialog.findViewById(R.id.dialogdescription);
                discription.setText("Enter the OTP!");
                Button okbtn=dialog.findViewById(R.id.dialogbtn);
                okbtn.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                dialog.show();
            }else if ((otp.length())!=6){
                Dialog dialog = new Dialog(OtpvarifyActivity.this);
                dialog.setContentView(R.layout.enterphonenumber);
                TextView discription = dialog.findViewById(R.id.dialogdescription);
                discription.setText("Enter the valid OTP!");
                Button okbtn=dialog.findViewById(R.id.dialogbtn);
                okbtn.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                dialog.show();
            }else {

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verivicationid, otp);
                signin(credential);
            }
        });

    }

    private void signin(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent=new Intent(OtpvarifyActivity.this, PrifileeditfirsttimeAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            Dialog dialog = new Dialog(OtpvarifyActivity.this);
                            dialog.setContentView(R.layout.enterphonenumber);
                            TextView discription = dialog.findViewById(R.id.dialogdescription);
                            discription.setText("Enter the valid OTP!");
                            Button okbtn=dialog.findViewById(R.id.dialogbtn);
                            okbtn.setOnClickListener(v1 -> {
                                dialog.dismiss();
                            });
                            dialog.show();
                        }
                    }
                });
    }
}