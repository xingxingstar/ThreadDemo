package com.example.zhuwojia.threaddemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author：shixinxin on 2017/4/26
 * version：v1.0
 */

public class MyService extends IntentService {
    Handler handler = new Handler();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public MyService(String name) {
        super(name);
    }

    public MyService() {
        super("");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String path = intent.getStringExtra("path");
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(path);
            File file = new File(Environment.getExternalStoragePublicDirectory(DOWNLOAD_SERVICE), "picture.jpg");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                fos = new FileOutputStream(file);
                byte[] data = new byte[1024];
                int len = 0;
                while ((len = is.read(data)) != -1) {
                    fos.write(data, 0, len);
                }
                fos.flush();
                Log.e("===ssss","图片下载成功");
              handler.post(new Runnable() {
                  @Override
                  public void run() {
                      Toast.makeText(getApplicationContext(),"图片下载完成",Toast.LENGTH_SHORT).show();
                  }
              });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(MyService.this,"图片下载失败",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MyService.this,"图片下载失败",Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
