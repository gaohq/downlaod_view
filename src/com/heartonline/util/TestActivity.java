package com.heartonline.util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import com.heartonline.download.R;
import com.heartonline.util.http.HttpHandler;

import java.io.File;

/**
 * Created by gaohaoqing
 * Date : 16/2/18
 * Time : 09:54
 */
public class TestActivity extends Activity {

    private ProgressBar progressBar;
    private Button button;
    private Button btn_stop;
    private HttpHandler<File> hh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DownLoadView downLoadView = (DownLoadView) findViewById(R.id.dlv_down);
//        downLoadView.setParameter("http://downmobile.kugou.com/Android/KugouPlayer/8000/KugouPlayer_219_V7.9.13.apk",
//                true,
//                Environment.getExternalStorageDirectory().getPath()+"/test.apk");
    }
//        progressBar = (ProgressBar) findViewById(R.id.pb_main);
//        button = (Button) findViewById(R.id.btn_state);
//        btn_stop = (Button) findViewById(R.id.btn_stop);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hh = initHttpHandler(progressBar);
//            }
//        });
//        btn_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hh.stop();
//            }
//        });
//
//    }
//
//    private HttpHandler<File> initHttpHandler(final ProgressBar progressBar) {
//        FinalHttp finalHttp = new FinalHttp();
//        HttpHandler<File> hh = finalHttp.download("http://downmobile.kugou.com/Android/KugouPlayer/8000/KugouPlayer_219_V7.9.13.apk",
//                true,
//                Environment.getExternalStorageDirectory().getPath()+"/test.apk",
//                new AjaxCallBack<File>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        Log.e("GG", "开始下载");
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                        super.onFailure(t, errorNo, strMsg);
//                        Log.e("GG", strMsg);
//                    }
//
//                    @Override
//                    public void onLoading(long count, long current) {
//                        int downloadedPercent = (int) ((current * 100) / count);
//                        Log.e("GG", "开始下载" + downloadedPercent);
//                        progressBar.setProgress(downloadedPercent);
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        super.onSuccess(file);
//                        Log.e("GG", "下载成功");
//                    }
//                });
//        return hh;
//    }
}
