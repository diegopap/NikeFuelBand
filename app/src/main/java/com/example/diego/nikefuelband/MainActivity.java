package com.example.diego.nikefuelband;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by diego on 05/10/15.
 */
public class MainActivity extends Activity {

    private int mProgressStatus = 0;
    private ProgressBar mProgress;
    private TextView mTextView;
    private Handler mHandler = new Handler();

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text_view);
        mProgress = (ProgressBar) findViewById(R.id.progress_bar);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/font.otf");
        mTextView.setTypeface(font);

        Runnable r = new Runnable() {
            public void run() {
                if (mProgressStatus <= mProgress.getMax()) {
                    mTextView.setText(String.valueOf(mProgressStatus));
                    mProgress.setProgress(mProgressStatus);
                    mHandler.postDelayed(this, 1);
                    mProgressStatus += 10;
                }
            }
        };

        // Start the progress bar
        mHandler.post(r);
    }
}