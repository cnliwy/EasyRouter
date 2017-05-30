package com.liwy.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.liwy.easyrouter.annotation.Router;
import com.liwy.easyrouter.router.RouterInit;
import com.liwy.easyrouter.router.Routers;

public class MainActivity extends AppCompatActivity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RouterInit.init();
        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Routers.open(MainActivity.this,"hello");
            }
        });
    }

    @Router("testRouterForJoke")
    public void testRouter(){
        System.out.println("hello world");
    }
}
