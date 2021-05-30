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
import androidx.appcompat.app.AppCompatActivity;
import com.invall.investproject.R;
import com.invall.investproject.model.ContentData;
import com.squareup.picasso.Picasso;

public class ContentActivity extends AppCompatActivity {
    ContentData contentData;
    TextView fullText;
    TextView titleText;
    ImageView fullpic;
    Button buttonInvest;

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
        buttonInvest.setText(SplashActivity.mData.getInvestbutton());

        if(SplashActivity.mData.getInvestads())
            buttonInvest.setVisibility(View.VISIBLE);

        buttonInvest.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashActivity.mData.getInvestlink()));
            startActivity(browserIntent);
        });

        contentData = getCurrentDataFromList();

        if(contentData != null){
            fullText.setText(contentData.getFulltext());
            titleText.setText(contentData.getCardtitle());
            Picasso.get().load(contentData.getFullpic()).into(fullpic);
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
}