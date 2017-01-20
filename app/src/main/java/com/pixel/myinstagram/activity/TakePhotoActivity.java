package com.pixel.myinstagram.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pixel.myinstagram.R;

/**
 * Created by zzr on 2017/1/20.
 */

public class TakePhotoActivity extends BaseActivity{

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    public static void startCameraFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TakePhotoActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
    }
}
