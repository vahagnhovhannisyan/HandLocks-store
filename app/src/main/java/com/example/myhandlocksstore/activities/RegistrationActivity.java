package com.example.myhandlocksstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    Button signUp;
    EditText name, email, password, commitPassword;
    TextView signIn;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseFirestore firestore;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        signUp = findViewById(R.id.signUpReg);
        name = findViewById(R.id.nameReg);
        email = findViewById(R.id.emailReg);
        password = findViewById(R.id.passwordReg);
        commitPassword = findViewById(R.id.commitPasswordReg);
        signIn = findViewById(R.id.signInReg);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                createUser();
                progressBar.setVisibility(View.VISIBLE);

            }

        });
    }
    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userCommitPassword = commitPassword.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Մուտքագրեք Ձեր Անունը", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Մուտքագրեք Ձեր email-ը", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Մուտքագրեք Ձեր գաղտնաբառը", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(userCommitPassword)){
            Toast.makeText(this,"Հաստատեք Ձեր գաղտնաբառը", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(userPassword.equals(userCommitPassword) == false){
            Toast.makeText(this,"Ձեր գաղտնաբառերը չեն համապատասխանում", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(userPassword.length() < 6){
            Toast.makeText(this,"Գաղտնաբառը Պետք Է Կազմված Լինի 6 Կամ Ավելի Նիշերից", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }





        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(RegistrationActivity.this,"Դուք հաջողությամբ գրանցվել եք", Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegistrationActivity.this,"Խնդրում ենք վերիֆիկացնել Ձեր email-ը` սեղմելով email-ին ուղարկված լինկին", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this,"email-ը գոյություն չունի կամ վերիֆիկացված չէ: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                    UserModel userModel = new UserModel(userName, userEmail, userPassword);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);
                    progressBar.setVisibility(View.GONE);

                    final HashMap<String,Object> cartMap0 = new HashMap<>();

                    cartMap0.put("name", userModel.getName());
                    cartMap0.put("email", userModel.getEmail());

                    firestore.collection("Users").add(cartMap0).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                        }
                    });


                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this,"email-ը գոյություն չունի կամ վերիֆիկացված չէ։" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}