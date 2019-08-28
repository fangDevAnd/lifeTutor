package com.example.componentasystemtest.photoZip;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.annotation.DrawableRes;
import androidx.collection.LruCache;

/**
 * 图片压缩的实现算法
 */
public class CompressUtil {


    /**
     * 计算压缩比例的代码
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * mImageView.setImageBitmap(
     * decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));这个是使用的方法
     *
     * @param res       #{{@link Resources}}
     * @param resId     #{R.drawable}
     * @param reqWidth  需要的宽度
     * @param reqHeight 需要的高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);//这个的作用只是去获得当前bitmap的宽高的值

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    static class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference imageViewReference;
        private int data;
        private Resources resources;
        private LruCache<String, Bitmap> lruCache;

        int requestWidth;
        int requestHeight;


        public BitmapWorkerTask(ImageView imageView, Resources resources, LruCache<String, Bitmap> lruCache) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference(imageView);//使用一个弱引用,来确保imageView能够正常的被垃圾回收器回收

            /**
             * 我们下面做出这样的猜测,因为我们的imageView受到activity的生命周期的影响,很可能会被回收,但是如果在这个函数中使用强引用,那么这个imageview因为强引用导致一种情况
             * 当我们的activity被销毁的时候,imageview因为这个函数的强引用,导致不能正常被回收  ,使用弱引用使得不在影响imageview的生命周期
             */

            requestWidth = imageView.getWidth();
            requestHeight = imageView.getHeight();
            this.resources = resources;
            this.lruCache = lruCache;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];//存放的是图片的地址

            return decodeSampledBitmapFromResource(resources, data, requestWidth, requestHeight);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                //如果imageview是可用的,我们记得缓存到内存
                lruCache.put((String) imageView.getTag(), bitmap);
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }


    /**
     * 异步加载图片
     * 并将加载的图片放入到内存中缓存
     *
     * @param resId
     * @param imageView
     * @param resources
     */
    public static void loadBitmap(@DrawableRes int resId, ImageView imageView, Resources resources, LruCache<? extends String, ? extends Bitmap> lruCache) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, resources, (LruCache<String, Bitmap>) lruCache);
        task.execute(resId);
    }


    static class BitMapCompressUtil {


        /**
         * 1.质量压缩
         * <p>
         * 质量压缩并不会改变图片在内存中的大小,仅仅会减小图片所占用的池盘空间的大小,因为质量压缩不会改变图片的分辨率,而图片在内存中的大小是根据图片的width*height
         * 的一个像素所占用的字节数计算的,宽高没变,在内存中占用的字节数也不会变,另外png是无损压缩,设置quality是无效的,下面是实现的代码
         *
         * @param format  需要进行压缩的格式
         * @param bitmap  进行压缩的bitmap
         * @param quality 质量  1-100  越小质量越差
         */
        public static void compress(Bitmap.CompressFormat format, int quality, Bitmap bitmap) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(format, quality, bos);

            File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(bos.toByteArray());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        /**
         * 采样滤压缩
         * #{{@link android.graphics.BitmapFactory.Options.inSampelSize}}来减少图片的分辨率
         * 进而减小图片所占用的磁盘空间和内存大小,
         * 设置  inSampleSize会导致压缩图片的宽高都为 1/inSampleSize  ,
         * 采样率压缩其原理是缩放bitamp的尺寸，通过调节其inSampleSize参数，比如调节为2，宽高会为原来的1/2，内存变回原来的1/4.
         *
         * @param bm            bitmap
         * @param compressRadio 压缩的比例,必须是2的平方的倍数
         */
        public static Bitmap compress1(Bitmap bm, int compressRadio) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = compressRadio;
            bm = BitmapFactory.decodeFile(Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/Camera/test.jpg", options);

            Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                    + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
            return bm;
        }
    }


}
