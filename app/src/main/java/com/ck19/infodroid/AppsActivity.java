package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btallapp; // 所有应用程序
    private Button btsystemapp;// 系统程序
    private Button btthirdapp; // 第三方应用程序
    private Button btsdcardapp; // 安装在SDCard的应用程序

    private int filter = AppinfosActivity.FILTER_ALL_APP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        // 初始化控件并监听
        btallapp = (Button) findViewById(R.id.btallapp);
        btsystemapp = (Button) findViewById(R.id.btsystemapp);
        btthirdapp = (Button) findViewById(R.id.btthirdapp);
        btsdcardapp =(Button) findViewById(R.id.btsdcardapp);
        btallapp.setOnClickListener(this);
        btsystemapp.setOnClickListener(this);
        btthirdapp.setOnClickListener(this);
        btsdcardapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        System.out.println(""+view.getId());
        switch(view.getId()){
            case R.id.btallapp	:
                filter = AppinfosActivity.FILTER_ALL_APP ;
                break ;
            case R.id.btsystemapp:
                filter = AppinfosActivity.FILTER_SYSTEM_APP ;
                break ;
            case R.id.btthirdapp:
                filter = AppinfosActivity.FILTER_THIRD_APP ;
                break ;
            case R.id.btsdcardapp:
                filter = AppinfosActivity.FILTER_SDCARD_APP ;
                break ;
        }
        Intent intent = new Intent(AppsActivity.this, AppinfosActivity.class) ;
        intent.putExtra("filter", filter) ;
        startActivity(intent);
    }
}
