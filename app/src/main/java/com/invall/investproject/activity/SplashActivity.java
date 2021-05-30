package com.invall.investproject.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.invall.investproject.R;
import com.invall.investproject.model.ContentData;
import com.invall.investproject.utility.Data;
import com.invall.investproject.utility.Setting;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SplashActivity extends AppCompatActivity {
    public static Data.Json mData;
    public static List<ContentData> contentDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_splash);

        Data data = new Data(this);
        data.getData((dataJson, context, result) -> {
            if (dataJson == null|| dataJson.isEmpty() || !result) {
                Intent intent = new Intent(context, ErrorActivity.class);
                context.startActivity(intent);
                finish();
                return;
            }
            mData = dataJson;
            prepareDefaultLang();
            castMapToArraylistContentData();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            finish();
        });
    }

    public static void castMapToArraylistContentData(){
        List<ContentData> allContentDataList = mData.getDataContent().entrySet()
                .stream()
                .map(e -> new ContentData(e.getKey(), e.getValue().get("cardimage"), e.getValue().get("cardtitle"), e.getValue().get("cardtext"),
                        e.getValue().get("fulltext"), e.getValue().get("fullpic"), e.getValue().get("investbutton"), e.getValue().get("investlink"),
                        e.getValue().get("local"), e.getValue().get("video")))
                .collect(Collectors.toList());

        contentDataList = allContentDataList.stream().filter(x -> x.getLocal().equals(getDeviceLang())).collect(Collectors.toList());

        if (contentDataList.isEmpty()){
            contentDataList = allContentDataList.stream().filter(x -> x.getLocal().equals(Setting.defaultLang)).collect(Collectors.toList());
        }
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    //в коде дефолт = en, но если есть в json, то пишем его как дефолт
    private void prepareDefaultLang(){
        if(mData.getDefaultLang() != null)
            Setting.defaultLang = mData.getDefaultLang();
    }

    //получить язык устройства
    private static String getDeviceLang(){
        return Locale.getDefault().getLanguage();
    }
}