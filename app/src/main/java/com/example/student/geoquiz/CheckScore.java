package com.example.student.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckScore extends AppCompatActivity {

    private Button mResetButton;
    private boolean mResetPressed;

    public static Intent newIntent(Context packageContext, boolean mResetPressed) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_score);

//        mResetPressed = getIntent().getBooleanExtra();

        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

