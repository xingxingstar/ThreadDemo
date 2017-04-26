package com.example.zhuwojia.threaddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.os.Environment.DIRECTORY_DCIM;

public class HandlerActivity extends AppCompatActivity {

    ImageView iv_show;
    Handler handler = new Handler();

    private static final int LOAD_SUCCESS = 1;
    private static final int LOAD_ERROR = -1;

    //    发送空消息
    Handler sendHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOAD_SUCCESS) {
                File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM), "pic.jpg");
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    iv_show.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    };
    //发送message携带信息
    Handler sendMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOAD_SUCCESS) {
                byte[] data = (byte[]) msg.obj;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv_show.setImageBitmap(bitmap);
            } else {
                Toast.makeText(HandlerActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        iv_show = (ImageView) findViewById(R.id.iv_show);
    }

    public void postClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    url = new URL("http://img4.imgtn.bdimg.com/it/u=3140064774,2673655868&fm=23&gp=0.jpg");
//                    开启连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    设置超时的时间，5000即5秒
                    conn.setConnectTimeout(5000);
//                    设置获取图片的方式为GET方式
                    conn.setRequestMethod("GET");
//                    获取连接的响应码，响应码为200时访问成功
                    if (conn.getResponseCode() == 200) {
//                        获取连接的输入流，这个输入流就是图片的输入流
                        is = conn.getInputStream();
//                        构建一个file对象存储图片
                        final File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM), "pic.jpg");
                        fos = new FileOutputStream(file);
                        int len = 0;
                        byte[] buffer = new byte[1024];
//                        将输入流写入到我们定义好的文件中
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
//                        将缓冲刷入文件
                        fos.flush();
//                        下载成功使用handler进行UI更新
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                iv_show.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    Log.e("====sssss", "下载失败");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
//    在最后，将各种流进行关闭
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e("====sssss", "下载失败");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void sendClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    url = new URL("http://img5.imgtn.bdimg.com/it/u=194685546,499818437&fm=23&gp=0.jpg");
//                    开启连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    设置超时的时间，5000即5秒
                    conn.setConnectTimeout(5000);
//                    设置获取图片的方式为GET方式
                    conn.setRequestMethod("GET");
                    conn.setUseCaches(true);
//                    获取连接的响应码，响应码为200时访问成功
                    if (conn.getResponseCode() == 200) {
//                        获取连接的输入流，这个输入流就是图片的输入流
                        is = conn.getInputStream();
//                        构建一个file对象存储图片
                        final File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM), "pic.jpg");
                        fos = new FileOutputStream(file);
                        int len = 0;
                        byte[] buffer = new byte[1024];
//                        将输入流写入到我们定义好的文件中
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
//                        将缓冲刷入文件
                        fos.flush();
//                        下载成功使用handler进行UI更新
                        sendHandler.sendEmptyMessage(LOAD_SUCCESS);
                    }
                } catch (MalformedURLException e) {
                    Log.e("====sssss", "下载失败");
                    sendHandler.sendEmptyMessage(LOAD_ERROR);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
//    在最后，将各种流进行关闭
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e("====sssss", "下载失败");
                        sendHandler.sendEmptyMessage(LOAD_ERROR);
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    //    handler使用message携带信息
    public void sendMessageClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    url = new URL("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=452870042,825861114&fm=23&gp=0.jpg");
//                    开启连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    设置超时的时间，5000即5秒
                    conn.setConnectTimeout(5000);
//                    设置获取图片的方式为GET方式
                    conn.setRequestMethod("GET");
                    conn.setUseCaches(true);
//                    获取连接的响应码，响应码为200时访问成功
                    if (conn.getResponseCode() == 200) {
//                        获取连接的输入流，这个输入流就是图片的输入流
                        is = conn.getInputStream();
//                        构建一个file对象存储图片
                        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
                        int rc = 0;
                        while ((rc = is.read(buff, 0, 100)) > 0) {
                            swapStream.write(buff, 0, rc);
                        }
                        byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
//                        将缓冲刷入文件
                        Message msg = Message.obtain();
                        msg.obj = in_b;
                        msg.what = LOAD_SUCCESS;
//                        下载成功使用handler进行UI更新
                        sendMessageHandler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    Log.e("====sssss", "下载失败");
                    sendMessageHandler.sendEmptyMessage(LOAD_ERROR);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
//    在最后，将各种流进行关闭
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e("====sssss", "下载失败");
                        sendMessageHandler.sendEmptyMessage(LOAD_ERROR);
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
