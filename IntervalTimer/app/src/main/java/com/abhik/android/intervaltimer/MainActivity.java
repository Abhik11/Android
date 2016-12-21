package com.abhik.android.intervaltimer;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Handler mHandler;
    EditText mTotalTimeEditText;
    EditText mIntervalEditText;
    Button mStart;
    TextView mTimerDisplayTextView;
    int mTotalTime;
    int mTimeInterval;
    int mIntervalCtr = 0;
    int mTotalTimeCtr = 0;
    String TAG = MainActivity.class.getSimpleName() + "CustomMarkers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        mTotalTimeEditText = (EditText) findViewById(R.id.totalTimeEditText);
        mTotalTimeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mTotalTimeEditText.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                mTotalTimeEditText.clearFocus();
                return false;
            }
        });
        mIntervalEditText = (EditText) findViewById(R.id.intervalEditText);
        mIntervalEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mIntervalEditText.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                mIntervalEditText.clearFocus();
                return false;
            }
        });
        mStart = (Button) findViewById(R.id.buttonStart);
        mTimerDisplayTextView = (TextView) findViewById(R.id.timerDisplay);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTotalTime = Integer.parseInt(mTotalTimeEditText.getText().toString());
                mTimeInterval = Integer.parseInt(mIntervalEditText.getText().toString());
                mIntervalCtr = 0;
                mTotalTimeCtr = 0;
                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, 0);
            }
        });
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            if (mIntervalCtr == mTimeInterval) {
                mIntervalCtr = 0;
                Log.v(TAG, "Elapsed");
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            }
            mIntervalCtr++;
            mTimerDisplayTextView.setText(
                    String.format("%02d", mTotalTimeCtr / 60) + ":"
                            + String.format("%02d", mTotalTimeCtr % 60));
            mTotalTimeCtr++;
            if (mTotalTimeCtr <= mTotalTime) {
                mHandler.postDelayed(this, 1000);
            }
        }
    };
}