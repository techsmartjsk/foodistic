
package com.futuristic.foodistic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.authentication.auth;

public class SplashActivity extends AppCompatActivity {
    private TextView textView;
    private static int SPLASH_SCREEN_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView=findViewById(R.id.textview);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/PunjabiVirsa.ttf");
        textView.setTypeface(customFont);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,
                        auth.class);

                startActivity(i);

                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

}




