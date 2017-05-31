package com.liwy.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liwy.easyrouter.annotation.Router;

@Router("hello")
public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String say = bundle.getString("say");
        String content = name + "说:" + say;
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
