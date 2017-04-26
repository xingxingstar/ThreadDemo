package com.example.zhuwojia.threaddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhuwojia.threaddemo.service.MyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //    Handler+Thread的实现
    public void handlerClick(View view) {
        Intent intent = new Intent(this, HandlerActivity.class);
        startActivity(intent);
    }

    //    AsyncTask的实现
    public void asyncTaskClick(View view) {
        Intent intent = new Intent(this, AsyncTaskActivity.class);
        startActivity(intent);
    }

    //    ThreadPoolExecutor的实现
    public void threadPoolClick(View view) {
        Intent intent = new Intent(this, ThreadPoolActivity.class);
        startActivity(intent);
    }

    //    IntentService的实现
    public void intentClick(View view) {
        String path = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1293775107,6809434&fm=23&gp=0.jpg";
        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("path",path);
        startService(intent);
    }
}
