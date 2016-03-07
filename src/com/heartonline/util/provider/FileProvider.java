package com.heartonline.util.provider;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.heartonline.download.R;

import java.io.File;

/**
 * Created by gaohaoqing
 * Date : 16/2/27
 * Time : 10:59
 */
public class FileProvider {
    private static boolean checkEndsWithInStringArray(String checkItsEnd,
                                                      String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    public static void openFile(Context context, File currentPath) {
        if (currentPath != null && currentPath.isFile()) {
            String fileName = currentPath.toString();
            Intent intent;
            if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingImage))) {
                intent = OpenFiles.getImageFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingWebText))) {
                intent = OpenFiles.getHtmlFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingPackage))) {
                intent = OpenFiles.getApkFileIntent(currentPath);
                context.startActivity(intent);

            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingAudio))) {
                intent = OpenFiles.getAudioFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingVideo))) {
                intent = OpenFiles.getVideoFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingText))) {
                intent = OpenFiles.getTextFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingPdf))) {
                intent = OpenFiles.getPdfFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingWord))) {
                intent = OpenFiles.getWordFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingExcel))) {
                intent = OpenFiles.getExcelFileIntent(currentPath);
                context.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, context.getResources().
                    getStringArray(R.array.fileEndingPPT))) {
                intent = OpenFiles.getPPTFileIntent(currentPath);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "对不起，这不是文件！", Toast.LENGTH_SHORT).show();
        }
    }
}
