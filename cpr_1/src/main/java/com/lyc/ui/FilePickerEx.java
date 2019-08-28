package com.lyc.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.widget.Toast;

import java.io.File;

/**
 *
 * 文件选择的picker
 */
public class FilePickerEx {

    private Activity ac;
    private int FILE_SELECT_CODE = 0;
    private String path;

    public FilePickerEx(Activity _ac, int _FILE_SELECT_CODE) {
        this.ac = _ac;
        this.FILE_SELECT_CODE = _FILE_SELECT_CODE;
    }

    public FilePickerEx(Activity _ac, String _path) {
        this.ac = _ac;
        this.path = _path;
    }

    public void show() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ac.startActivityForResult(Intent.createChooser(intent, "选择文件"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(ac, "not found this activity", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void open() {
        File file = new File(path);
        if (null == file || !file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            ac.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     @JavascriptInterface public void openFileDir(String path){
     if(filepick == null){
     filepick = new FilePickerEx(this.mc,path);
     filepick.open();
     }
     }
     *
     **/
}
