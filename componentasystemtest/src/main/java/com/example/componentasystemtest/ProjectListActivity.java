package com.example.componentasystemtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.componentasystemtest.AlarmManager.AlarmActivity;
import com.example.componentasystemtest.Annomation.MyActivityDemo7;
import com.example.componentasystemtest.AppEnabled.AppEnableActivity;
import com.example.componentasystemtest.ColorMetrix.MyActivity;
import com.example.componentasystemtest.DecorView.DectorActivity;
import com.example.componentasystemtest.Fragment.FragmentTackActivity;
import com.example.componentasystemtest.JobScheduler.simple2.JobSchedulerActivity;
import com.example.componentasystemtest.JobScheduler.simple3.JobScheActivity;
import com.example.componentasystemtest.Lifecycle.ObserableActivity;
import com.example.componentasystemtest.MediaPlayer.MediaPlayerActivity;
import com.example.componentasystemtest.MediaPlayer.VideoPlayer1Activity;
import com.example.componentasystemtest.MediaPlayer.VideoPlayerActivity;
import com.example.componentasystemtest.MediaPlayer.demo.MusicPlayActivity;
import com.example.componentasystemtest.ScreenTest.ScreenTestActivity;
import com.example.componentasystemtest.TabedActivity.TabedActivity;
import com.example.componentasystemtest.appProtectedAlive.doubleProcessProtected.AppProtectedActivity;
import com.example.componentasystemtest.appProtectedAlive.jobScheduler.JobActivity;
import com.example.componentasystemtest.appProtectedAlive.nullNotification.NullNotificationActivity;
import com.example.componentasystemtest.appProtectedAlive.onePixelActivity.OnePixelActivity;
import com.example.componentasystemtest.appProtectedAlive.onePixelActivity.SinglePixelActivity;
import com.example.componentasystemtest.asyncTask.AsyncActivity;
import com.example.componentasystemtest.asyncTask.LoaderActivity;
import com.example.componentasystemtest.broadcastTest.baseUse.MainActivity;
import com.example.componentasystemtest.broadcastTest.netStatusChange.NetChangeActivity;
import com.example.componentasystemtest.canvas.MainActivity.Activity;
import com.example.componentasystemtest.canvas.MainActivity.MyViewGroupTestActivity;
import com.example.componentasystemtest.consumeViewAttribute.AttrActivity;
import com.example.componentasystemtest.density.Density;
import com.example.componentasystemtest.dialog.Simple2.DialogWidthTestActivity;
import com.example.componentasystemtest.dialog.simple1.DialogActivity;
import com.example.componentasystemtest.dialog.simple1.MyDialogFragment;
import com.example.componentasystemtest.downloadService.activity.DownloadFileTestActivity;
import com.example.componentasystemtest.downloadService.activity.EnvirnmentFilePathTestActivity;
import com.example.componentasystemtest.editText_inputManager_trans.InputManager_Activity;
import com.example.componentasystemtest.fireWall.FireWallActivity;
import com.example.componentasystemtest.handler.HandlerPrincipleActivity;
import com.example.componentasystemtest.moveLinearViewDisplay.LinearActivity;
import com.example.componentasystemtest.musicPlay.simple2.MusicListActivity;
import com.example.componentasystemtest.net.NetRequestActivity;
import com.example.componentasystemtest.notification.MyNotificationActivity;
import com.example.componentasystemtest.palette.MyPaletteActivity;
import com.example.componentasystemtest.photoZip.PhotoZipActivity;
import com.example.componentasystemtest.popupWindow.PopWindowActivity;
import com.example.componentasystemtest.scrollview.Activity.HerizontalScrollViewGroupActivity;
import com.example.componentasystemtest.scrollview.Activity.ScrollViewDemo1Activity;
import com.example.componentasystemtest.serializable.fragmentSerializableTest.FragmentSerizlizableActivity;
import com.example.componentasystemtest.sqlite.DaoOprateActivity;
import com.example.componentasystemtest.surface.Demo3Activity;
import com.example.componentasystemtest.view.ViewBgActivity;
import com.example.componentasystemtest.windowManager.WindowManagerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity {


    ListView listView;

    Class[] array = {
            DownloadFileTestActivity.class,
            NetRequestActivity.class,
            InputManager_Activity.class,
            DialogWidthTestActivity.class,
            Density.class,
            VideoPlayer1Activity.class,
            VideoPlayerActivity.class,
            AsyncActivity.class,
            HandlerPrincipleActivity.class,
            AlarmActivity.class,
            MyActivityDemo7.class,
            AppEnableActivity.class,
            AppProtectedActivity.class,
            JobActivity.class,
            NullNotificationActivity.class,
            OnePixelActivity.class,
            SinglePixelActivity.class,
            LoaderActivity.class,
            MainActivity.class,
            NetChangeActivity.class,
            MyViewGroupTestActivity.class,
            MyActivity.class,
            AttrActivity.class,
            DectorActivity.class,
            DialogActivity.class,
            MyDialogFragment.class,
            DialogWidthTestActivity.class,
            DownloadFileTestActivity.class,
            EnvirnmentFilePathTestActivity.class,
            FireWallActivity.class,
            FragmentTackActivity.class,
            com.example.componentasystemtest.JobScheduler.simple1.JobActivity.class,
            JobSchedulerActivity.class,
            JobScheActivity.class,
            ObserableActivity.class,
            MediaPlayerActivity.class,
            LinearActivity.class,
            MusicPlayActivity.class,
            MusicListActivity.class,
            com.example.componentasystemtest.musicPlay.simple3.MediaPlayerActivity.class,
            MyNotificationActivity.class,
            MyPaletteActivity.class,
            PhotoZipActivity.class,
            PopWindowActivity.class,
            ScreenTestActivity.class,
            HerizontalScrollViewGroupActivity.class,
            ScrollViewDemo1Activity.class,
            com.example.componentasystemtest.scrollview.viewGroup.MyActivity.class,
            FragmentSerizlizableActivity.class,
            DaoOprateActivity.class,
            Demo3Activity.class,
            TabedActivity.class,
            ViewBgActivity.class,
            com.example.componentasystemtest.viewDragHelper.MyActivity.class,
            WindowManagerActivity.class

    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_list);


        listView = findViewById(R.id.list);

        List<String> strings = new ArrayList<>();

        for (Class classO : array) {
            strings.add(classO.getName());
        }


        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.pro_list_item,
                strings));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class aClass = array[position];
                Intent intent = new Intent(ProjectListActivity.this, aClass);
                startActivity(intent);
            }
        });
    }
}
