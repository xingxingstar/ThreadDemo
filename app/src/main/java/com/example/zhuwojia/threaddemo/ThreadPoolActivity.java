package com.example.zhuwojia.threaddemo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhuwojia.threaddemo.utils.DownloadService;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolActivity extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
    }

    public void onClick(View view) {
        String path = Environment.getExternalStoragePublicDirectory(DOWNLOAD_SERVICE).getAbsolutePath();
        new DownloadService(path, getListUrl(), new DownloadService.DownloadStateListener() {

            @Override
            public void onFinish() {
                //图片下载成功后，实现您的代码
//                此处为子线程，要实现需要到主线程中执行
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ThreadPoolActivity.this, "图片下载成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailed() {
                //图片下载成功后，实现您的代码

            }
        }).startDownload();


    }

    public List<String> getListUrl() {
        List<String> listUrl = new ArrayList<>();
        listUrl.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=757442881,2807937503&fm=23&gp=0.jpg");
        listUrl.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813565807,555821885&fm=23&gp=0.jpg");
        listUrl.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2852425674,621177370&fm=23&gp=0.jpg");
        return listUrl;
    }
}
