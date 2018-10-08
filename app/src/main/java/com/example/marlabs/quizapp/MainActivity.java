package com.example.marlabs.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static final String SHARED_PREFS = "sharedPref";
    public static final String Key_Score = "highScore";
    private TextView txthighScore;
    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txthighScore = findViewById(R.id.txt_high_score);
        loadHighScore();
        Button quizStart = findViewById(R.id.btn_start);
        quizStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highScore){
                    updateHighScore(score);
                }
            }
        }
    }

    private  void  loadHighScore(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highScore = preferences.getInt(Key_Score, 0);
        txthighScore.setText("HidhScore: " + highScore);
    }
    private void updateHighScore(int highScoreNew) {
        highScore = highScoreNew;
        txthighScore.setText("HidhScore: " + highScore);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key_Score, highScore);
        editor.apply();
    }
}
