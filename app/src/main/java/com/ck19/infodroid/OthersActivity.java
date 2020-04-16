package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.HashMap;

public class OthersActivity extends AppCompatActivity {

    private HashMap results = new HashMap<String, Object>();
    private InformationAdapter adapter = null;

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
    }
}
