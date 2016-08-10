package com.example.cb.testijkplayer3.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.cb.testijkplayer3.R;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.widget.Settings;
import tv.danmaku.ijk.media.player.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.player.widget.media.IjkVideoView;

public class MainActivity extends AppCompatActivity {
    private IjkVideoView mVideoView;
    private Settings mSettings;
    private String mVideoPath;
    private AndroidMediaController mMediaController;
    private boolean mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mSettings = new Settings(this);

        mVideoPath = "rtsp://111.44.243.114/live/030101111000032-1/1";

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mMediaController = new AndroidMediaController(this, false);

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                Toast.makeText(MainActivity.this, what + " error", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:

                        break;
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mVideoView.start();
                        break;
                }
                return false;
            }
        });

        mVideoView.setVideoPath(mVideoPath);
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mVideoView.start();
            }
        });
//        mVideoView.start();
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainAcitvity", "onStop");
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
//            mVideoView.stopPlayback();
//            mVideoView.release(true);
//            mVideoView.stopBackgroundPlay();
//        } else {
//            mVideoView.enterBackground();
//        }
//        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("MainAcitvity", "onConfigurationChanged");
        Log.d("MainAcitvity", "onConfigurationChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainAcitvity","onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainAcitvity", "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainAcitvity", "onPause");
    }
}
