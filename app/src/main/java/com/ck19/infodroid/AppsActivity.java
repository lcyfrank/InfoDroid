package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.HashMap;

public class AppsActivity extends AppCompatActivity {

    private HashMap results = new HashMap<String, Object>();
    private InformationAdapter adapter = null;

    private HashMap<String, Object> getInformation() {
        // Object can be String or String[]
        // results.put(key, value);
        // ...
        getPackageList(this);

        return results;
    }

    private void getPackageList(Context ctx) {
        PackageManager packManager = ctx.getPackageManager();
        String[] appUid = null;
        int uid = 1000;
        while (uid <= 19999) {
            appUid = packManager.getPackagesForUid(uid);
            if (appUid != null && appUid.length > 0) {
                for (String item : appUid) {
                    try {
                        final PackageInfo packageInfo = packManager.getPackageInfo(item, 0);
                        if (packageInfo == null) {
                            break;
                        }
                        CharSequence applicationLabel = packManager.getApplicationLabel(packManager.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));
                        results.put(applicationLabel.toString(), packageInfo.packageName);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            uid++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        HashMap<String, Object> results = getInformation();
        adapter = new InformationAdapter(AppsActivity.this, results);
        ListView listView = findViewById(R.id.list_system);
        listView.setAdapter(adapter);
    }
}
