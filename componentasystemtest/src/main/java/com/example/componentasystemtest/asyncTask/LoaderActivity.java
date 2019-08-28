package com.example.componentasystemtest.asyncTask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.componentasystemtest.R;

public class LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        AsyncTaskLoader asyncTaskLoader = new AsyncTaskLoader<String>(getBaseContext()) {

            @Override
            public String loadInBackground() {
                //该方法运行在子线程中
                SystemClock.sleep(5000);
                return "返回了数据";
            }
        };


        return asyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Toast.makeText(this, "加载数据完成" + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


}
