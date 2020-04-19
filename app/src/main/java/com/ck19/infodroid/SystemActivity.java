package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

public class SystemActivity extends AppCompatActivity {

    private HashMap results = new HashMap<String, Object>();
    private InformationAdapter adapter = null;

    private HashMap<String, Object> getInformation() {

        // Object can be String or String[]
        // results.put(key, value);
        // ...

        //BOARD
        String[] BOARD_ARRAY = new String[2];
        BOARD_ARRAY[0] = "BOARD: " + android.os.Build.BOARD;
        BOARD_ARRAY[1] = "BOOTLOADER: " + android.os.Build.BOOTLOADER;//引导程序版本
        results.put("BOARD", BOARD_ARRAY);

        //BRAND 运营商
        String[] BRAND_ARRAY = new String[3];
        BRAND_ARRAY[0] = "BRAND: " + android.os.Build.BRAND;
        BRAND_ARRAY[1] = "CPU_ABI: " + android.os.Build.CPU_ABI;//指令集
        BRAND_ARRAY[2] = "CPU_ABI2: " + android.os.Build.CPU_ABI2;//第二指令集
        results.put("BRAND", BRAND_ARRAY);

        //DEVICE 驱动
        results.put("DEVICE", android.os.Build.DEVICE);

        //DISPLAY Rom的名字 例如 Flyme 1.1.2（魅族rom） &nbsp;JWR66V（Android nexus系列原生4.3rom）
        results.put("DISPLAY", android.os.Build.DISPLAY);

        //指纹
        results.put("FINGERPRINT", android.os.Build.FINGERPRINT);

        //HARDWARE 硬件
        String[] HARDWARE_ARRAY = new String[3];
        HARDWARE_ARRAY[0] = "HARDWARE: " + android.os.Build.HARDWARE;
        HARDWARE_ARRAY[1] = "HOST: " + android.os.Build.HOST;
        HARDWARE_ARRAY[2] = "ID: " + android.os.Build.ID;//设备版本号
        results.put("HARDWARE", HARDWARE_ARRAY);

        //MANUFACTURER 生产厂家
        results.put("MANUFACTURER", android.os.Build.MANUFACTURER);

        //MODEL 机型
        String[] MODEL_ARRAY = new String[7];
        MODEL_ARRAY[0] = "MODEL: " + android.os.Build.MODEL;
        MODEL_ARRAY[1] = "PRODUCT: " + android.os.Build.PRODUCT;
        MODEL_ARRAY[2] = "RADIO: " + android.os.Build.RADIO;
        MODEL_ARRAY[3] = "DEVICE TAG: " + android.os.Build.TAGS;
        MODEL_ARRAY[4] = "TIME: " + android.os.Build.TIME;
        MODEL_ARRAY[5] = "TYPE: " + android.os.Build.TYPE;//设备版本号
        MODEL_ARRAY[6] = "USER: " + android.os.Build.USER;
        results.put("MODEL", MODEL_ARRAY);

        //VERSION.RELEASE 固件版本
        String[] FIRMWARE_ARRAY = new String[2];
        FIRMWARE_ARRAY[0] = "VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        FIRMWARE_ARRAY[1] = "VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME;
        results.put("FIRMWARE", FIRMWARE_ARRAY);

        //VERSION.INCREMENTAL 基带版本
        results.put("VERSION.INCREMENTAL", android.os.Build.VERSION.INCREMENTAL);

        //VERSION.SDK SDK版本
        String[] SDK_ARRAY = new String[2];
        SDK_ARRAY[0] = "VERSION.SDK: " + android.os.Build.VERSION.SDK;
        SDK_ARRAY[1] = "VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT;
        results.put("SDK", SDK_ARRAY);

        return results;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        HashMap<String, Object> results = getInformation();
        adapter = new InformationAdapter(SystemActivity.this, results);
        ListView listView = findViewById(R.id.list_system);
        listView.setAdapter(adapter);
    }
}
