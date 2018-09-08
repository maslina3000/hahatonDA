package com.example.asus.hahatonda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


public void onClick(View v) {
    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
}
