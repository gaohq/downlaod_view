package com.heartonline.util.core;

import java.io.File;

/**
 * Created by gaohaoqing
 * Date : 16/2/29
 * Time : 11:00
 */
public class FileUtil {

    /**
     * 创建文件夹
     *
     * @param folderPath
     */
    public static void createFolder(String folderPath) {
        File file = new File(folderPath);
        file.mkdirs();
    }

    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    public static boolean isHaveFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
