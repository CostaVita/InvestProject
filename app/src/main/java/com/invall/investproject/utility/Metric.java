package com.invall.investproject.utility;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class Metric extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("594941f8-5238-4e5b-b710-087cb49b2d57").build();
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);
    }

}
