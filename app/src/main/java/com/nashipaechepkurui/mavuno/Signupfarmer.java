package com.nashipaechepkurui.mavuno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.nashipaechepkurui.mavuno.app.MyApplication;
import com.nashipaechepkurui.mavuno.app.User;

import java.util.Map;

public class Signupfarmer extends AppCompatActivity {

    private Firebase ref;

    private User user;
    private EditText name;
    private EditText phone;
    private EditText password;
    private EditText password2;
    private Button signup;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupfarmer);

        //Creating firebase object
        ref = new Firebase(MyApplication.FIREBASE_URL);
    }
    @Override
    protected void onStart() {
        super.onStart();
        name = (EditText) findViewById(R.id.etname);
        phone = (EditText) findViewById(R.id.etphone);
        password = (EditText) findViewById(R.id.etpassword);
        password2 = (EditText) findViewById(R.id.etpassword2);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_sign_up);
    }



    //getting data from fields
    protected void setUpUser() {
        user = new User();
        user.setName(name.getText().toString());
        user.setPhone(phone.getText().toString());
        // Create an alias email for the user "0712345678@mavuno.app"
        user.setEmail(phone.getText().toString());
        user.setPassword(password.getText().toString());
    }

    public void onSignUpClicked(View view) {
        //Validate inputs
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(getBaseContext(), "name required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(getBaseContext(), "phone number required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getBaseContext(), "password required", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.equals(password2.getText().toString(), password.getText().toString())) {
            Toast.makeText(getBaseContext(), "Password Dont Match", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            setUpUser();
            //createUser method creates a new user account with the given email and password.
            //Parameters are :
            // email - The email for the account to be created
            // password - The password for the account to be created
            // handler - A handler which is called with the result of the operation
            ref.createUser(
                    user.getEmail(),
                    user.getPassword(),
                    new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {
                            user.setId(stringObjectMap.get("uid").toString());
                            user.saveUser();
                            ref.unauth();
                            Toast.makeText(getApplicationContext(), "Your Account has been Created", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Please Login With your Phone and Password", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            //Go to Login Screen
                            startActivity(new Intent(getApplicationContext(), Loginfarmer.class));
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
            );
        }
    }
}
