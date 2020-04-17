package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IntentHijack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_hijack);
        Intent intent = getIntent();
        IntentHijackUtil util = new IntentHijackUtil(intent.getData().toString());
        InformationDao dao;
        dao = new InformationDao(IntentHijack.this);
        dao.insert("Operation", util.getOperation(), 2);
        finish();
    }
}
