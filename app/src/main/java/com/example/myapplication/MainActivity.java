package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int counter;
    UserLocalStore storeUser;
    EditText email, password;
    Button loginButton, signupButton;
    User user;
    Context context;
    Receiver receiver = new Receiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        storeUser = new UserLocalStore(context);
        email = (EditText) findViewById(R.id.mail_Login);
        password = (EditText) findViewById(R.id.password_Login);
        loginButton = (Button) findViewById(R.id.login_button);
        signupButton = (Button) findViewById(R.id.signup_button);
        counter = 0;

        IntentFilter filter = new IntentFilter("com.example.EXAMPLE_ACTION");
        registerReceiver(receiver, filter);
    }

    public void login(View view) {
        user = storeUser.getLoggedInUser();
        Context context = getApplicationContext();
        System.out.println(user.getEMail() + user.getPassword());
        int duration = Toast.LENGTH_SHORT;
        System.out.println(email.getText().toString() + password.getText().toString());
        if (Authenticate(email.getText().toString(), password.getText().toString())){
            CharSequence text = "succesfull";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent activity2Intent = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(activity2Intent);
        } else {
            counter++;
            if(counter >= 3) {
                loginButton.setEnabled(false);
            }
            CharSequence text = "error";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private boolean Authenticate(String email, String password){
        if(user.getEMail().equals(email) && user.getPassword().equals(password) ){
            return true;
        }
        return false;
    }

    public void signup(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(activity2Intent);
    }
}