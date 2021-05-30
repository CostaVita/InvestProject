package com.invall.investproject.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.invall.investproject.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class Data {

    private final Activity activity;
    private static Boolean update = true;

    public Data(Activity activity){
        this.activity = activity;
    }

    public interface CompleteCallback {
        void onComplete(Json dataJson, Context context, boolean result);
    }

    public void getData (CompleteCallback completeCallback){
        if (update) {
            DownloadJSON downloadJSON = new DownloadJSON(completeCallback);
            downloadJSON.execute();
        }
        try {
            FileInputStream fis = activity.openFileInput("settings.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            Log.e("Json",e.getMessage());
        }
    }

    private void setData (String newData){
        FileOutputStream outputStream;
        try {
            outputStream = activity.openFileOutput("settings.json", Context.MODE_PRIVATE);
            outputStream.write(newData.getBytes());
            outputStream.close();
            update = false;
        } catch (Exception e) {
            Log.e("Json",e.getMessage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadJSON extends AsyncTask<String, String, String> {

        private final CompleteCallback completeCallback;

        DownloadJSON(CompleteCallback completeCallback) {
            this.completeCallback = completeCallback;
        }

        protected String doInBackground(String... input) {
            try {
                BufferedReader reader = null;
                try {
                    URL url = new URL(activity.getResources().getString(R.string.linkJSON));
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder buffer = new StringBuilder();
                    int read;
                    char[] chars = new char[1024];
                    while ((read = reader.read(chars)) != -1)
                        buffer.append(chars, 0, read);
                    return buffer.toString();
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error result " + e.toString());
                    return null;
                } finally {
                    if (reader != null)
                        reader.close();
                }
            } catch (Exception e) {
                Log.e("Json", e.getMessage());

            }
            return null;
        }



        protected void onPostExecute(String json) {
            if (json != null) {
                setData(json);
                Json data = new Gson().fromJson(json, Json.class);
                completeCallback.onComplete(data, activity, true);
            } else {
                completeCallback.onComplete(null, activity, false);
            }
        }
    }

    public static class Json {
        private Map<String, Map<String, String>> contentData;
        private Boolean investads;
        private String investlink;
        private String investbutton;

        public Map<String, Map<String, String>> getDataContent() { return contentData; }
        public void setContentData(Map<String, Map<String, String>> contentData) { this.contentData = contentData; }

        public boolean isEmpty() {
            return this.contentData == null;
        }

        public Boolean getInvestads() {
            return investads;
        }

        public String getInvestlink() {
            return investlink;
        }

        public String getInvestbutton() {
            return investbutton;
        }
    }
}