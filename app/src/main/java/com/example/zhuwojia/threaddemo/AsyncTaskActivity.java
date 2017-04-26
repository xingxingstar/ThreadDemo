package com.example.zhuwojia.threaddemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.zhuwojia.threaddemo.task.MyAsyncTask;

public class AsyncTaskActivity extends AppCompatActivity implements MyAsyncTask.CallBack {

    ImageView iv_show;
    ProgressBar pb_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        pb_dialog = (ProgressBar) findViewById(R.id.pb_dialog);
    }

    public void onClick(View view) {
        MyAsyncTask task = new MyAsyncTask(pb_dialog);
        task.setCallBack(this);
        task.execute("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=678832275,837077055&fm=23&gp=0.jpg");
    }

    @Override
    public void callback(Bitmap bitmap) {
        iv_show.setImageBitmap(bitmap);
    }
}
