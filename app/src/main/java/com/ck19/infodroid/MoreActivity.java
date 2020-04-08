package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        TextView textView = findViewById(R.id.text_more_desc);
        textView.setText("Designed By CK 19\n\nJust for learning only.");

        TextView rightView = findViewById(R.id.text_more_right);
        rightView.setText("All rights reserved.");
    }
}
