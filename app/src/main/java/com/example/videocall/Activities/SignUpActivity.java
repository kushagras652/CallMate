package com.example.videocall.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.videocall.R;
import com.example.videocall.utilities.Constants;
import com.example.videocall.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputFirstName,inputLastname,inputEmail,inputPassword;
    private Button buttonSignUp;
    private ProgressBar pb;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        preferenceManager=new PreferenceManager(getApplicationContext());

        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        inputFirstName=findViewById(R.id.enterName);
        inputLastname=findViewById(R.id.enterLastName);
        inputEmail=findViewById(R.id.enterEmail);
        inputPassword=findViewById(R.id.enterPassword);
        buttonSignUp=findViewById(R.id.signupButton);
        pb=findViewById(R.id.progressBar);

        findViewById(R.id.account).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        });
        buttonSignUp.setOnClickListener(view -> {
            String fName=inputFirstName.getText().toString().trim();
            String lName=inputLastname.getText().toString().trim();
            String eEmail=inputEmail.getText().toString().trim();
            String pPassword=inputPassword.getText().toString().trim();
            if(TextUtils.isEmpty(fName)){
                inputFirstName.setError("Enter First Name");
                return;
            }
            if(TextUtils.isEmpty(lName)){
                inputLastname.setError("Enter Last Name");
                return;
            }
            if(TextUtils.isEmpty(eEmail)){
                inputEmail.setError("Enter Email");
                return;
            }
            if(TextUtils.isEmpty(pPassword)){
                inputPassword.setError("Enter Password");
                return;
            }
            signup();
        });
    }

    private void signup() {
        buttonSignUp.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);

        FirebaseFirestore database=FirebaseFirestore.getInstance();
        HashMap<String,Object> user=new HashMap<>();
        user.put(Constants.KEY_FIRST_NAME,inputFirstName.getText().toString());
        user.put(Constants.KEY_LAST_NAME,inputLastname.getText().toString());
        user.put(Constants.KEY_EMAIL,inputEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD,inputPassword.getText().toString());

        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_FIRST_NAME,inputFirstName.getText().toString());
                    preferenceManager.putString(Constants.KEY_LAST_NAME,inputLastname.getText().toString());
                    preferenceManager.putString(Constants.KEY_EMAIL,inputEmail.getText().toString());
                    preferenceManager.putString(Constants.KEY_PASSWORD,inputPassword.getText().toString());
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    pb.setVisibility(View.INVISIBLE);
                    buttonSignUp.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}