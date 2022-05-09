package com.example.asl_buddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button registerr;
    private EditText editname,editage,editemail,editpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        registerr=findViewById(R.id.button5);
        registerr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerUser();
            }

        }

        );

        editname=(EditText) findViewById(R.id.editTextTextPersonName);
        editage=(EditText) findViewById(R.id.editTextNumber);
        editemail=(EditText) findViewById(R.id.editTextTextEmailAddress2);
        editpassword=(EditText) findViewById(R.id.editTextTextPassword2);
    }
    /*
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button5:
                registerUser();
                break;


        }
    }
*/
    private void registerUser()
    {
        String email=editemail.getText().toString().trim();
        String password=editpassword.getText().toString().trim();
        String fullname=editname.getText().toString().trim();
        String age=editage.getText().toString().trim();

        if(fullname.isEmpty())
        {
            editname.setError("Full name Required!");
            editname.requestFocus();
            return;
        }
        if(age.isEmpty())
        {
            editage.setError("Age Required!");
            editage.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editemail.setError("Age Required!");
            editemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editemail.setError("Valid email Please!");
            editemail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editpassword.setError("Password Required!");
            editpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            User user=new User(fullname,age,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        finish();
                                        Toast.makeText(sign_up.this,"Success",Toast.LENGTH_LONG).show();

                                    }
                                    else
                                    {
                                        finish();
                                        Toast.makeText(sign_up.this,"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else
                            {
                                finish();
                                Toast.makeText(sign_up.this,"Failed",Toast.LENGTH_LONG).show();
                            }
                    }
                });

    }
}