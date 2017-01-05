package com.blessing;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blessing.util.SharedPreferencesHandler;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.btnBack)
    Button btnBack;
    @Bind(R.id.tvNumber)
    TextView tvNumber;
    @Bind(R.id.imgTap)
    FrameLayout imgTap;
    @Bind(R.id.btnReset)
    Button btnReset;
    @Bind(R.id.layoutBlackTrans)
    ImageView layoutBlackTrans;

    int tap_count;
    private MediaPlayer mPlayer;
    private SharedPreferencesHandler spHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setUI();

        imgTap.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_DOWN)) {
                    tap_count++;
                    tvNumber.setText(String.valueOf(tap_count));
                    openSound();
                    spHandler.setPreferences("tap_count", tap_count);
                    layoutBlackTrans.setVisibility(View.VISIBLE);
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    layoutBlackTrans.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    @OnClick(R.id.btnBack)
    public void onClickBackHeader() {
        finish();
    }

    @OnClick(R.id.btnReset)
    public void onClickbtnReset() {
        tap_count = 0;
        tvNumber.setText(String.valueOf(tap_count));
        spHandler.setPreferences("tap_count", tap_count);
    }

    private void setUI() {
        spHandler = new SharedPreferencesHandler(this);
        tap_count = spHandler.getIntSavedPreferences("tap_count");
        tvNumber.setText(String.valueOf(tap_count));
    }

    private void openSound() {
        try {
            if (mPlayer != null && mPlayer.isPlaying())
                mPlayer.reset();
            mPlayer = MediaPlayer.create(this, R.raw.e);
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mPlayer.start();
    }
}
