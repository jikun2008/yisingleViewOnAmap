package com.yisingle.study.map.one.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yisingle.amapview.lib.utils.CustomAnimator;
import com.yisingle.study.map.one.R;

/**
 * @author jikun
 * Created by jikun on 2018/6/6.
 */
public class ThreadActivity extends Activity {

    private CustomAnimator customAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_thread);
        customAnimator = new CustomAnimator();
    }

    public void start(View view) {
        customAnimator.start();

    }


    public void cancle(View view) {
        customAnimator.end();

    }
}
