package com.ck19.infodroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "lcy";

    Button systemButton = null;
    Button appsButton = null;
    Button usersButton = null;
    Button othersButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Setup Header BG View */
        View bgView = findViewById(R.id.view_main_bg);
        int colors[] = {0xFF6031C0, 0xFF4430BF};
        GradientDrawable bgDrawble = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        bgView.setBackground(bgDrawble);

        TextView title = findViewById(R.id.text_main_title);
        title.setTextSize(60);
        title.setTextColor(Color.WHITE);

        TextView desc = findViewById(R.id.text_main_desc);
        String mainDesc = "Get information from Android devices.";
        desc.setText(mainDesc);
        desc.setTextSize(14);
        desc.setTextColor(Color.WHITE);

        /* Setup Buttons */
        systemButton = findViewById(R.id.button_system);
        systemButton.setOnClickListener(this);

        appsButton = findViewById(R.id.button_apps);
        appsButton.setOnClickListener(this);

        usersButton = findViewById(R.id.button_users);
        usersButton.setOnClickListener(this);

        othersButton = findViewById(R.id.button_others);
        othersButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_main_more) {
            Intent intent = new Intent(MainActivity.this, MoreActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_system: {
                Intent intent = new Intent(MainActivity.this, SystemActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.button_apps: {
                Intent intent = new Intent(MainActivity.this, AppsActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.button_users: {
                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.button_others: {
                Intent intent = new Intent(MainActivity.this, OthersActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
