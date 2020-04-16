package com.ck19.infodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        TextView textView = findViewById(R.id.text_more_desc);
        textView.setText("Designed By CK 19\n\nJust for learning only.");

        TextView rightView = findViewById(R.id.text_more_right);
        rightView.setText("All rights reserved.");

        Button clearButton = findViewById(R.id.button_clear);
        clearButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        InformationDialogFragment informationDialogFragment = new InformationDialogFragment();
        informationDialogFragment.show("Clear all?", "Clear all informations saved in this device?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InformationDao dao = new InformationDao(MoreActivity.this);
                dao.clearAll();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, getSupportFragmentManager());
    }
}


