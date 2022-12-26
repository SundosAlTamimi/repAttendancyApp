package com.example.employeetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView SingUpButton;
    TextView SingInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();

    }

    private void initialization() {
        SingInButton=findViewById(R.id.SingInButton);
        SingUpButton=findViewById(R.id.SingUpButton);

        SingInButton.setOnClickListener(Clicked);
        SingUpButton.setOnClickListener(Clicked);
    }


    View.OnClickListener Clicked=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.SingInButton:
                    Intent intSingIn = new Intent(MainActivity.this, SingInActivity.class);
                    startActivity(intSingIn);
                    break;
                case R.id.SingUpButton:
                    Intent intSingUp = new Intent(MainActivity.this, SingOutActivity.class);
                    startActivity(intSingUp);
                    break;

            }

        }
    };

}