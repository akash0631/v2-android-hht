package com.v2retail.dotvik;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashScreen.this, IPActivity.class));

        finish();

    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
