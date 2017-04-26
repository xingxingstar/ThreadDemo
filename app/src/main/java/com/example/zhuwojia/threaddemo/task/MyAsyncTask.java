package com.example.zhuwojia.threaddemo.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.os.Environment.DIRECTORY_DCIM;

/**
 * author：shixinxin on 2017/4/25
 * version：v1.0
 */

public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    //尖括号内的内容分别为参数、进度、返回值类型
    public interface CallBack {
        void callback(Bitmap bitmap);
    }

    ProgressBar progressBar;
    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public MyAsyncTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        第一个执行方法，进行预备工作
        progressBar.setProgress(0);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
//        第二个执行方法，在onPreExecute执行完成后执行
        InputStream is = null;
        FileOutputStream fos = null;
        final File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM), "pic.jpg");
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {

                is = conn.getInputStream();
//                获取文件总长度
                int total_len = conn.getContentLength();
                fos = new FileOutputStream(file);
                byte[] data = new byte[1024];
                int len = 0;
                int value = 0, current = 0;
                while ((len = is.read(data)) != -1) {
                    current += len;
                    value = (int) ((float) current / total_len * 100);
//                    通知更新进度条
                    publishProgress(value);
                    fos.write(data, 0, len);
                }
                fos.flush();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//       这个函数在doInBackground调用publishProgress时触发，虽然调用时只有一个参数
        //但是这里取到的是一个数组,所以要用progesss[0]来取值
        //第n个参数就用progress[n]来取值
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//         doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
        //这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕"
        super.onPostExecute(bitmap);
//        使用接口回调返回结果到主线程中
        if (callBack != null) {
            callBack.callback(bitmap);
        }
    }
}
