package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OthersActivity extends AppCompatActivity {

    private static String TAG = "lcy";

    private HashMap results = new HashMap<String, Object>();
    private InformationAdapter adapter = null;
    private OkHttpClient client = new OkHttpClient();
    private String url = "http://127.0.0.1:59777";

    Call post(String url, String key, String value, Callback callback) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();

        try {
            json.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }


    private String[] parseResponse(String response, String key) {
        String jsonStr = response;
        jsonStr = jsonStr.split("\\[")[1].split("]")[0];
        String[] jsonList = jsonStr.split("\n");

        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < jsonList.length; ++i) {
            String jsonObjectStr = jsonList[i];
            if (jsonObjectStr.length() <= 3) continue;
            jsonObjectStr = jsonObjectStr.substring(0, jsonObjectStr.length() - 5);
            jsonObjectStr += "}";
            try {
                JSONObject object = new JSONObject(jsonObjectStr);
                items.add(object.getString(key));
            } catch (JSONException e) {

            }
        }
        return items.toArray(new String[items.size()]);
    }

    private void getApps() {
        post(url, "command", "listAppsAll", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseString = response.body().string();
                if (responseString.contains("NullPointerException")) return;

                String jsonStr = responseString;
                jsonStr = jsonStr.split("\\[")[1].split("]")[0];
                String[] jsonList = jsonStr.split("\n");

                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < jsonList.length; ++i) {
                    String jsonObjectStr = jsonList[i];
                    if (jsonObjectStr.length() <= 3) continue;
                    jsonObjectStr = jsonObjectStr.substring(0, jsonObjectStr.length() - 2);
//                    jsonObjectStr += "}";
                    try {
                        JSONObject object = new JSONObject(jsonObjectStr);
                        items.add(object.getString("location"));
                    } catch (JSONException e) {

                    }
                }
                for (int i = 0; i < items.size(); ++i) {
                    String[] itemList = items.get(i).split("/");
                    String itemString = itemList[itemList.length - 1];
                    itemString = itemString.substring(0, itemString.length() - 4);
                    items.set(i, itemString);
                    Log.i(TAG, "onResponse: " + items.get(i));
                }
                results.put("Apps", items.toArray(new String[items.size()]));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void getESContents(final String key, String command) {
        post(url, "command", command, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseString = response.body().string();
                if (responseString.contains("NullPointerException")) return;
                results.put(key, parseResponse(responseString, "name"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
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
        setContentView(R.layout.activity_others);

        HashMap<String, Object> results = getInformation();
        adapter = new InformationAdapter(OthersActivity.this, results);
        ListView listView = findViewById(R.id.list_system);
        listView.setAdapter(adapter);

        getESContents("Files", "listFiles");
        getESContents("Pictures", "listPics");
        getESContents("Videos", "listVideos");
        getESContents("Audios", "listAudios");
        getApps();
    }
}
