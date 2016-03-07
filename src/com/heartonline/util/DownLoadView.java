package com.heartonline.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.heartonline.download.R;
import com.heartonline.util.core.AsyncTask;
import com.heartonline.util.core.FileUtil;
import com.heartonline.util.http.AjaxCallBack;
import com.heartonline.util.http.HttpHandler;
import com.heartonline.util.provider.FileProvider;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by gaohaoqing
 * Date : 16/2/18
 * Time : 17:19
 */
public class DownLoadView extends LinearLayout {

    //下载进行中布局
    private LinearLayout ll_ldc_progress;
    //下载开始布局
    private LinearLayout ll_ldc_start;
    //下载完成布局
    private LinearLayout ll_ldc_preview;

    //文字进行
    private TextView tv_ldp_content_progress;
    //进度条
    private ProgressBar pgb_download_progress;
    //暂停按钮
    private ImageView btn_ldp_pause_download;
    //开始按钮
    private Button btn_lds_start_download;
    //预览
    private Button btn_ldp_preview;

    //下载地址
    private String mDownloadUrl;
    //下载线程
    private HttpHandler<File> mDownLoadFileHandler;
    //下载存放路径
    private String mDownloadPath;
    //下载文件名字
    private String mFileName;
    //是否断点下载
    private boolean mIsBreakpoint;
    //下载配置
    private static FinalHttp mFinalHttp;

    private enum StateType {
        PROGRESS, START, PREVIEW
    }

    public DownLoadView(Context context) {
        super(context);
        init(context);
    }

    public DownLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DownLoadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    static {
        mFinalHttp = new FinalHttp();
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_download_control, this);

        ll_ldc_progress = (LinearLayout) findViewById(R.id.ll_ldc_progress);
        ll_ldc_start = (LinearLayout) findViewById(R.id.ll_ldc_start);
        ll_ldc_preview = (LinearLayout) findViewById(R.id.ll_ldc_preview);

        tv_ldp_content_progress = (TextView) ll_ldc_progress.findViewById(R.id.tv_ldp_content_progress);
        pgb_download_progress = (ProgressBar) ll_ldc_progress.findViewById(R.id.pgb_download_progress);
        btn_ldp_pause_download = (ImageView) ll_ldc_progress.findViewById(R.id.btn_ldp_pause_download);
        btn_ldp_pause_download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseDownLoad();
            }
        });
        btn_lds_start_download = (Button) ll_ldc_start.findViewById(R.id.btn_lds_start_download);
        btn_lds_start_download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownLoad();
            }
        });
        btn_ldp_preview = (Button) ll_ldc_preview.findViewById(R.id.btn_ldp_preview);
        btn_ldp_preview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                preViewFile();
            }
        });

        changeArea(StateType.START);
    }

    private void preViewFile() {
        File file = new File(mDownloadPath + "/" + mFileName);
        FileProvider.openFile(getContext(), file);
    }

    /**
     * 设置参数
     *
     * @param downloadUrl  下载路径
     * @param isBreakpoint 是否支持断点f
     * @param downloadPath 下载存放路径
     */
    public void setParameter(String downloadUrl, Boolean isBreakpoint, String downloadPath, String fileName) {
        this.mDownloadUrl = downloadUrl;
        this.mIsBreakpoint = isBreakpoint;
        this.mDownloadPath = downloadPath;
        this.mFileName = fileName;
        FileUtil.createFolder(downloadPath);
        isFinished(mDownloadPath + "/" + mFileName, mDownloadUrl);

    }

    private void isFinished(String localName, String download) {
        final File localFile = new File(localName);
        final String sofaString = URLUtil.returnSafeURL(download);
        new AsyncTask<Boolean, Void, Integer>() {

            @Override
            protected Integer doInBackground(Boolean... params) {
                try {
                    return URLUtil.getFileSize(sofaString);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if (localFile.exists() && localFile.length() == integer) {
                    changeArea(StateType.PREVIEW);
                } else {
                    changeArea(StateType.START);
                }
            }
        }.execute(true);
    }


    /**
     * 开始下载进程
     */
    private void startDownLoad() {
        mDownLoadFileHandler = initDownLoadHandler();
        changeArea(StateType.PROGRESS);
    }


    /**
     * 暂停下载进程
     */
    private void pauseDownLoad() {
        if (mDownLoadFileHandler != null && !mDownLoadFileHandler.isStop()) {
            mDownLoadFileHandler.stop();
            changeArea(StateType.START);
            btn_lds_start_download.setText(R.string.download_continue);
        }
    }

    /**
     * 初始化下载进程
     *
     * @return
     */
    private HttpHandler<File> initDownLoadHandler() {
        Log.e("download", "下载路径:" + mDownloadUrl);
        String safeDownloadUrl = URLUtil.returnSafeURL(mDownloadUrl);
        mDownLoadFileHandler = mFinalHttp.download(safeDownloadUrl,
                mIsBreakpoint,
                mDownloadPath + "/" + mFileName,
                new AjaxCallBack<File>() {
                    @Override
                    public void onSuccess(File file) {
                        super.onSuccess(file);
                        changeArea(StateType.PREVIEW);
                        Toast.makeText(getContext(), R.string.download_finish, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoading(long count, long current) {
                        super.onLoading(count, current);
                        setProgressAndContent(count, current);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        Log.e("download", "失败原因" + errorNo + strMsg);
                        if (errorNo != 0)
                            Toast.makeText(getContext(), R.string.download_failure, Toast.LENGTH_LONG).show();
                    }
                });
        return mDownLoadFileHandler;
    }

    /**
     * 改变区域
     *
     * @param stateType
     */
    private void changeArea(StateType stateType) {
        switch (stateType) {
            case PROGRESS:
                setAreaShow(ll_ldc_progress);
                break;
            case START:
                setAreaShow(ll_ldc_start);
                break;
            case PREVIEW:
                setAreaShow(ll_ldc_preview);
                break;
        }
    }

    /**
     * 设置进度条和文字
     *
     * @param count   总数
     * @param current 当前
     */
    private void setProgressAndContent(long count, long current) {
        String currentMB = String.format("%.2f", current / 1024 / 1024.00) + "MB";
        String countMB = String.format("%.2f", count / 1024 / 1024.00) + "MB";
        tv_ldp_content_progress.setText(currentMB + "/" + countMB);
        int downloadedPercent = (int) ((current * 100) / count);
        pgb_download_progress.setProgress(downloadedPercent);
    }

    /**
     * 设置区域的显隐
     *
     * @param layout
     */
    private void setAreaShow(LinearLayout layout) {
        ll_ldc_progress.setVisibility(View.GONE);
        ll_ldc_start.setVisibility(View.GONE);
        ll_ldc_preview.setVisibility(View.GONE);

        layout.setVisibility(View.VISIBLE);
    }
}
