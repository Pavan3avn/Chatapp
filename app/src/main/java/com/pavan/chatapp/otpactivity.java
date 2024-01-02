package com.pavan.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otpactivity extends AppCompatActivity {

    String phonenum;
    Long timeout = 60L;

    String verficationcode;

    PhoneAuthProvider.ForceResendingToken ResendingToken;
    Button btn;
    EditText otp;
    TextView resendtext;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        btn = findViewById(R.id.btn);
        otp = findViewById(R.id.otp);
        resendtext = findViewById(R.id.resendtext);

        phonenum = getIntent().getExtras().getString("phone");
        sendotp(phonenum,false);

        btn.setOnClickListener(v -> {
            String enteredotp = otp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationcode,enteredotp);
            signin(credential);

        });

        resendtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendotp(phonenum,true);

            }
        });
    }

    void sendotp(String phoneNumber,boolean isResend){
        startresendtimer();
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeout, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    signin(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(otpactivity.this, "Otp verification failed", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verficationcode = s;
                                ResendingToken = forceResendingToken;
                                Toast.makeText(otpactivity.this, "Otp sent suceessfully", Toast.LENGTH_SHORT).show();

                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }


    }

    void startresendtimer() {
        resendtext.setEnabled(false);
        final Handler handler = new Handler(Looper.getMainLooper());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeout--;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        resendtext.setText("Resend the otp " + timeout + " seconds");
                    }
                });

                if (timeout <= 0) {
                    timeout = 60L;
                    timer.cancel();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            resendtext.setEnabled(true);
                        }
                    });
                }
            }
        }, 0, 1000);
    }

    void signin(PhoneAuthCredential phoneAuthCredential){

        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Intent intent = new Intent(otpactivity.this,usernameactivity.class);
                    intent.putExtra("phone",phonenum);
                    startActivity(intent);

                }else{Toast.makeText(otpactivity.this,"verification failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}