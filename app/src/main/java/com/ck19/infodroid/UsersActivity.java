package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                results.put("IP", response.body().string());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private HashMap<String, Object> getInformation() {
        // Object can be String or String[]
        // results.put(key, value);
        // ...
        Log.i(TAG, "getInformation: " + results);
        return results;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        adapter = new InformationAdapter(UsersActivity.this, results);
        ListView listView = findViewById(R.id.list_system);
        listView.setAdapter(adapter);

        getIp();
    }
}
