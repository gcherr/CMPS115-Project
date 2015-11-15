package com.example.will.slugsports;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by will on 11/15/15.
 */
public class App extends Application{

    @Override public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "7st18qTMNhNJICNJx1hY5cbk8BzSKB99fKx1qCgP", "zeyvANSw3bh0yLOiPtQJ05052qaKFNIaV7cP83Og");
    }
}
