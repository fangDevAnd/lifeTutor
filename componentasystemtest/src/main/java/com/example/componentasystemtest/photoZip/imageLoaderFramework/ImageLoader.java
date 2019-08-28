package com.example.componentasystemtest.photoZip.imageLoaderFramework;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.LruCache;

import androidx.annotation.RequiresPermission;

import java.io.File;

import okhttp3.internal.cache.DiskLruCache;

public class ImageLoader {

    private LruCache<String, Bitmap> mMemoryCache;

    private DiskLruCache diskLruCache;


    private Context context;


    private ImageLoader(Context context) {
        this.context = context.getApplicationContext();

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

        File diskCacheDir = getDiskCacheDir(context, "bitmap");

    }

    //    @RequiresPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public File getDiskCacheDir(Context context, String folder) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), folder);
    }
}
