package com.example.componentasystemtest.musicPlay.simple2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;

public class MusicBitmapIndex {

    private Context context;

    private static MusicBitmapIndex musicBitmapIndex;


    private MusicBitmapIndex(Context context) {
        this.context = context;
    }

    public static MusicBitmapIndex getInstance(Context context) {
        if (musicBitmapIndex == null) {
            musicBitmapIndex = new MusicBitmapIndex(context);
        }
        return musicBitmapIndex;
    }


    public void fetchBitmapAsync(final Uri uri, MyAdapter.ViewHolder viewHolder, int id) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                return retrieveBitmap(context, uri);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap == null) {
                    return;
                }
                viewHolder.setBitmap(id, bitmap);
            }
        }.execute();
    }

    private Bitmap retrieveBitmap(Context context, Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, uri);
        byte[] embeddedPicture = retriever.getEmbeddedPicture();
        if (embeddedPicture != null && embeddedPicture.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.length);
            return bitmap;
        }
        return null;
    }


}
