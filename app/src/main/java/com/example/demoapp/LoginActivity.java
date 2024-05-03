package com.example.demoapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText=findViewById(R.id.editTextNumber);
        editText.requestFocus();
        button =findViewById(R.id.buttonGetOTP);
        progressBar =findViewById(R.id.progressBar);
        sentotp();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenumber=editText.getText().toString().trim();
               // Integer number = Integer.parseInt(phonenumber);
                if (phonenumber.isEmpty()){
                    Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.setContentView(R.layout.enterphonenumber);
                    Button okbtn=dialog.findViewById(R.id.dialogbtn);
                    okbtn.setOnClickListener(v1 -> {
                        dialog.dismiss();
                    });
                    dialog.show();
                }else if ((phonenumber.length())!=10){
                    Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.setContentView(R.layout.enterphonenumber);
                    TextView discription = dialog.findViewById(R.id.dialogdescription);
                    discription.setText("Enter the valid Phone number!");
                    Button okbtn=dialog.findViewById(R.id.dialogbtn);
                    okbtn.setOnClickListener(v1 -> {
                        dialog.dismiss();
                    });
                    dialog.show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
//                    Dialog dialog=new Dialog(LoginActivity.this);
//                    dialog.setContentView(R.layout.loading);
                    button.setVisibility(View.INVISIBLE);
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber("+91"+phonenumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(LoginActivity.this)                 // (optional) Activity for callback binding
                                    // If no activity is passed, reCAPTCHA verification can not be used.
                                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });
    }
    private void sentotp(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this,"Errot"+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                Intent intent= new Intent(LoginActivity.this, OtpvarifyActivity.class);
                intent.putExtra("id",verificationId);
                intent.putExtra("phone",phonenumber);
                startActivity(intent);

            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginActivity.this, TabbedActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                           finish();

                        } else {
                          Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}