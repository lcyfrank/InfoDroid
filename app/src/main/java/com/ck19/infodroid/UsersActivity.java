package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class UsersActivity extends AppCompatActivity {

    private static String TAG = "lcy";
    private static String Url = "http://127.0.0.1:5000";

    private HashMap results = new HashMap<String, Object>();
    private InformationAdapter adapter = null;
    private OkHttpClient client = new OkHttpClient();

    Call get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private void getIp() {
        String url = "http://47.96.178.173:8080/ip";
        get(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                String ip = response.body().string();
                results.put("IP", ip);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String ip = (String) results.get("IP");
//                        results.remove("IP");
                        adapter.notifyDataSetChanged();
                        getLocation((String) ip);
                    }
                });
            }
        });
    }

    private void getLocation(String ip) {
        String url = "http://ip-api.com/json/" + ip + "?lang=zh-CN";
        get(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonStr = response.body().string();
                try {
                    JSONObject json = new JSONObject(jsonStr);
                    String country = json.getString("country");
                    String region = json.getString("regionName");
                    String city = json.getString("city");
                    results.put("Location", country + " " + region + " " + city);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException exception) {
                    Log.e(TAG, "onResponse: " + exception.getLocalizedMessage());
                }
            }
        });
    }

    private void getStack() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info: appProcessInfos) {
            Log.i(TAG, "getStack: " + info.processName);
        }
    }

    private void getSharedDirectory() {
        String titles[] = {"Music", "Notifications", "Pictures", "Movies", "Downloads", "Documents"};
        File files[] = new File[titles.length];
        // 音乐
        files[0] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        // 通知
        files[1] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
        // 图片
        files[2] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // 视频
        files[3] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        // 下载
        files[4] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // 文档
        files[5] =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        // 闹钟
//        File alarms = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        // 播客
//        File podcasts = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
        // 铃声
//        File ringtones = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);

        for (int i = 0; i < titles.length; ++i) {
            String title = titles[i];
            File file = files[i];
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                String fileNames[] = new String[listFiles.length];
                for (int j = 0; j < listFiles.length; ++j) {
                    fileNames[i] = listFiles[i].getName();
                }
                results.put(title, fileNames);
                adapter.notifyDataSetChanged();
            }
        }

    }

    private HashMap<String, Object> getInformation() {
        // Object can be String or String[]
        // results.put(key, value);
        // ...
        return results;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        getInformation();
        adapter = new InformationAdapter(UsersActivity.this, results);
        ListView listView = findViewById(R.id.list_system);
        listView.setAdapter(adapter);

        getIp();
//        getStack();
        getSharedDirectory();
    }
}
