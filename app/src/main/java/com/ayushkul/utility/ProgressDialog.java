package com.ayushkul.utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.Window;

import com.ayushkul.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class ProgressDialog extends Dialog {

    private AVLoadingIndicatorView aviLoader;

    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ProgressDialog(Context context, boolean cancelable, CancellationSignal.OnCancelListener cancelListener) {
        super(context, cancelable, (OnCancelListener) cancelListener);
    }

    public static ProgressDialog createProgressDialog(Context context) {
        return new ProgressDialog(context);
    }

    public static ProgressDialog createProgressDialog(Context context, boolean isCancelable, CancellationSignal.OnCancelListener cancelListener) {

        return new ProgressDialog(context, isCancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_common_loader);
        aviLoader = (AVLoadingIndicatorView) findViewById(R.id.avi_loader);
        aviLoader.show();
    }

    @Override
    public void onDetachedFromWindow() {
        aviLoader.hide();
        super.onDetachedFromWindow();
    }
}