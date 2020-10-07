package com.zerrium.uts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout emailLayout, passwordLayout;
    private EditText email, password;
    private Button login;
    private CheckBox show_pass;
    private int  version = Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        show_pass = (CheckBox) findViewById(R.id.checkBoxShowPass);
        emailLayout = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        passwordLayout = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                password.setTransformationMethod(b ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_pass = password.getText().toString();

                if(text_email.isEmpty()){
                    emailLayout.setError("Please enter e-mail address");
                    return;
                }

                if(text_pass.isEmpty()){
                    passwordLayout.setError("Please enter password");
                    return;
                }

                if(text_email.equalsIgnoreCase("admin@mail.com") && text_pass.equals("admin123")){
                    Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    emailLayout.setError(null);
                    passwordLayout.setError(null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Incorrect e-mail address or password!").setNegativeButton("Retry", null).create().show();
                }
            }
        });
    }
}