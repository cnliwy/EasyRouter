package com.liwy.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liwy.easyrouter.annotation.Router;

@Router("hello")
public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
    }
}
