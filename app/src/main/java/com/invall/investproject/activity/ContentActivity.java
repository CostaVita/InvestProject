package com.invall.investproject.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.invall.investproject.R;
import com.invall.investproject.model.ContentData;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class ContentActivity extends AppCompatActivity {
    ContentData contentData;
    TextView fullText;
    TextView titleText;
    ImageView fullpic;
    Button buttonInvest;
    Boolean isVideoContent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String getContentKeyFromExtra(){ return getIntent().getStringExtra("contentKey"); };

    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_content);

        fullText = findViewById(R.id.fullText);
        titleText = findViewById(R.id.title);
        fullpic = findViewById(R.id.fullpic);
        fullText.setMovementMethod(new ScrollingMovementMethod());
        buttonInvest = findViewById(R.id.investadsbut);

        if(SplashActivity.mData.getInvestads())
            buttonInvest.setVisibility(View.VISIBLE);

        contentData = getCurrentDataFromList();

        if(contentData != null){
            prepareVideo();
            buttonInvest.setText(contentData.getInvestbutton());
            buttonInvest.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contentData.getInvestlink()));
                startActivity(browserIntent);
            });

            fullText.setText(contentData.getFulltext());
            titleText.setText(contentData.getCardtitle());
            if (!isVideoContent){
                Picasso.get().load(contentData.getFullpic()).into(fullpic);
            } else {
                fullpic.setVisibility(View.GONE);
            }
            setTitle(contentData.getCardtitle());
        }
    }

    private ContentData getCurrentDataFromList(){
        return SplashActivity.contentDataList.stream()
                .filter(x -> x.getContentKey().equals(getContentKeyFromExtra()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void prepareVideo(){
        if (!contentData.getVideo().equals("")){
            isVideoContent = true;
            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.setVisibility(View.VISIBLE);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = contentData.getVideo();
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }
    }
}