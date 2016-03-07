package com.heartonline.util;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by gaohaoqing
 * Date : 16/3/2
 * Time : 11:58
 */
public class URLUtil {
    /**
     * 返回安全的URL
     *
     * @param downloadUrl 没转义之前的URL
     * @return
     */
    public static String returnSafeURL(String downloadUrl) {
        String hostURL = downloadUrl.substring(0, downloadUrl.lastIndexOf("/"));
        String url = downloadUrl.substring(downloadUrl.lastIndexOf("/"), downloadUrl.length());
        String safeURL = "";
        try {
            safeURL = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("download", "转义失败");
            return "";
        }
        return hostURL + safeURL;
    }

    /**
     * get file size 通过URL地址获取文件大小
     *
     * @param filePath * url file path
     * @return filesize
     * @throws MalformedURLException
     */
    public static int getFileSize(String filePath) throws MalformedURLException {
        HttpURLConnection urlcon = null;
        int filesize = 0;
        //create url link
        URL url = new URL(filePath);
        try {
            //open url
            urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setRequestProperty("Accept-Encoding", "identity");
            //get url properties
            filesize = urlcon.getContentLength();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close connect
            urlcon.disconnect();
        }
        return filesize;
    }
}
