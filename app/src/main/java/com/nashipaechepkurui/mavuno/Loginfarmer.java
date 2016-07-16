package com.nashipaechepkurui.mavuno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.nashipaechepkurui.mavuno.app.MyApplication;
import com.nashipaechepkurui.mavuno.app.User;

public class Loginfarmer extends AppCompatActivity {

    private Firebase ref;
    private User user;
    private EditText userPhone;
    private EditText userPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ref = new Firebase(MyApplication.FIREBASE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userPhone = (EditText) findViewById(R.id.etloginPhoneNumber);
        userPassword = (EditText) findViewById(R.id.etloginPassword);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_login);
        checkUserLogin();
    }

    protected void setUpUser() {
        user = new User();
        // USER PHONE IS THE EMAIL
        user.setEmail(userPhone.getText().toString());
        user.setPassword(userPassword.getText().toString());
    }

    public void onLoginClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        setUpUser();
        aunthenticateUserLogin();
    }


    private void checkUserLogin() {
        //getAuth Returns the current authentication state of the Firebase client. If the client is unauthenticated, this method will return null.
        // Otherwise, the return value will be an object containing at least the fields such as uid,provider,token,expires,auth
        // https://www.firebase.com/docs/web/api/firebase/getauth.html,
        if (ref.getAuth() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            String uid = ref.getAuth().getUid();
            intent.putExtra("user_id", uid);
            startActivity(intent);
            finish();
        }
    }


    private void aunthenticateUserLogin() {
        //authWithPassword method attempts to authenticate to Firebase with the given credentials.
        //Paramters Are :
        // email - The email for the user to authenticate
        // password - The password for the account
        // handler - A handler which will be called with the result of the authentication attempt
        ref.authWithPassword(
                user.getEmail(),
                user.getPassword(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //Log.i("TOKEN",authData.getToken());
                        //Log.i("PROVIDER",authData.getProvider());
                        //Log.i("UID",authData.getUid());
                        //Log.i("AUTH_MAP",authData.getAuth().toString());
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(), FarmerDetails.class));
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
    }
}
