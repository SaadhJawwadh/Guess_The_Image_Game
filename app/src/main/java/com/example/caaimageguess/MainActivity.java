package com.example.caaimageguess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchTimer;
    boolean isTimerSwitchOn = false;

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchTimer = findViewById(R.id.switch_timer);

        //To change the boolean value when the switch button clicked
        switchTimer.setOnCheckedChangeListener((buttonView, isChecked) -> isTimerSwitchOn = isChecked);
    }

    //*https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-1-get-started/lesson-2-activities-and-intents/2-1-c-activities-and-intents/2-1-c-activities-and-intents.html
    public void launchCarMakeActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!"); //*https://developer.android.com/studio/debug/am-logcat
        Intent intent = new Intent(this, CarMake.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

    public void launchHintActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, Hint.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

    public void launchCarImageActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, CarImage.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

    public void launchAdvanceActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, AdvanceLevel.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }
}