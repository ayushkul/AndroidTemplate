package com.ayushkul.utility.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResourceId = layoutResourceId();

        setContentView(layoutResourceId);
        unbinder = ButterKnife.bind(this);

        onActivityLaunched();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void launchActivity(Class<?> activity, boolean finish) {
        startActivity(new Intent(this, activity));
        if (finish) finish();
    }


    protected void launchActivity(Intent intent, boolean finish) {
        startActivity(intent);
        if (finish)
            finish();
    }

    protected void launchActivity(Intent intent, Bundle bundle, boolean finish) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
        if (finish) finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    protected abstract @LayoutRes
    int layoutResourceId();

    protected abstract void onActivityLaunched();

}
