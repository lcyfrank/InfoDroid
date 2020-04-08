package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.HashMap;

public class UsersActivity extends AppCompatActivity {

    private HashMap<String, Object> getInformation() {
        HashMap results = new HashMap<String, String>();
        // Object can be String or String[]
        // results.put(key, value);
        // ...

        return results;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        HashMap<String, Object> results = getInformation();
        InformationAdapter adapter = new InformationAdapter(UsersActivity.this, results);
        ListView listView = findViewById(R.id.list_system);
        listView.setAdapter(adapter);
    }
}
