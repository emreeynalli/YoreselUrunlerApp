package com.example.yreselrnler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LauncherActivity extends AppCompatActivity {
    private TextView launcher;
    private static int gecis_suresi = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        launcher = findViewById(R.id.launcher_text);

        //Animasyon
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.launcher_animator);
        launcher.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },gecis_suresi);
    }
}