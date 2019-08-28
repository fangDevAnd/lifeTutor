package com.example.momomusic.dao;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.*;

public class MusicDataDbTest {

    @Test
    public void queryTableRows() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MusicDataDb musicDataDb = MusicDataDb.getInstance(context);
        System.out.print("当前的行" + musicDataDb.queryTableRows("msuic"));
    }
}