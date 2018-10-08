package com.example.marlabs.quizapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    public static final long COUNTDOWN = 30000;
    private TextView txtque;
    private TextView txtscore;
    private TextView txtqcnt;
    private TextView txtcntdwn;

    private RadioGroup rdgrp;
    private RadioButton rd1;
    private RadioButton rd2;
    private RadioButton rd3;
    private Button btnconfirm;

    private ColorStateList colorStateList;
    private ColorStateList defualtclr;
    private CountDownTimer countDownTimer;
    private long timeleft;

    private List<Questions> questionsList;
    private int queCounter;
    private int queTotal;
    private Questions currentQue;

    private int score;
    private boolean answered;
    private long bpt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtque = findViewById(R.id.txt_que);
        txtscore = findViewById(R.id.txt_view_score);
        txtqcnt = findViewById(R.id.txt_que_count);
        txtcntdwn = findViewById(R.id.txt_time);

        rdgrp = findViewById(R.id.rad_group);
        rd1 = findViewById(R.id.rad_btn1);
        rd2 = findViewById(R.id.rad_btn2);
        rd3 = findViewById(R.id.rad_btn3);

        colorStateList = rd1.getTextColors();
        btnconfirm = findViewById(R.id.btn_confirm);

        DBQuiz dbQuiz = new DBQuiz(this);
        questionsList = dbQuiz.getAllQue();
        queTotal = questionsList.size();
        Collections.shuffle(questionsList);

        showNextQue();
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered){
                    if (rd1.isChecked() || rd2.isChecked() || rd3.isChecked()){
                        checkAns();
                    }else {
                        Toast.makeText(QuizActivity.this, "Select answer!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkAns() {
        answered = true;
        countDownTimer.cancel();
        RadioButton radioButton = findViewById(rdgrp.getCheckedRadioButtonId());
        int ansno = rdgrp.indexOfChild(radioButton) + 1;

        if (ansno == currentQue.getAns_no()){
            score++;
            txtscore.setText("Score: " + score);
        }
        showSolution();
    }

    private void showSolution() {
        rd1.setTextColor(Color.RED);
        rd2.setTextColor(Color.RED);
        rd3.setTextColor(Color.RED);

        switch (currentQue.getAns_no()){
            case 1:
                rd1.setTextColor(Color.GREEN);
                txtque.setText("Ans 1 is Correct!");
                break;
            case 2:
                rd2.setTextColor(Color.GREEN);
                txtque.setText("Ans 2 is Correct!");
                break;
            case 3:
                rd3.setTextColor(Color.GREEN);
                txtque.setText("Ans 3 is Correct!");
                break;
        }
        if (queCounter < queTotal){
            btnconfirm.setText("Next..!");
        }else {
            btnconfirm.setText("Finish..!");
        }
    }

    private void showNextQue() {
        rd1.setTextColor(colorStateList);
        rd2.setTextColor(colorStateList);
        rd3.setTextColor(colorStateList);
        rdgrp.clearCheck();

        if (queCounter < queTotal){
            currentQue = questionsList.get(queCounter);
            txtque.setText(currentQue.getQue());
            rd1.setText(currentQue.getOption1());
            rd2.setText(currentQue.getOption2());
            rd3.setText(currentQue.getOption3());

            queCounter++;
            txtqcnt.setText("Question: " + queCounter + "/" + queTotal);
            answered = false;
            btnconfirm.setText("Confirm");
            timeleft = COUNTDOWN;
            startCount();
        }else {
            finishQuiz();
        }
    }

    private void startCount() {
        countDownTimer = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long l) {
                timeleft = l;
                updateCount();
            }

            @Override
            public void onFinish() {
                timeleft = 0;
                updateCount();
                checkAns();
            }
        }.start();
    }

    private void updateCount() {
        int min = (int) ((timeleft / 1000) / 60);
        int sec = (int) ((timeleft / 1000) % 60);
        String tf = String.format(Locale.getDefault(), "%2d:%2d", min, sec);
        txtcntdwn.setText(tf);
        if (timeleft < 10000 ){
            txtcntdwn.setTextColor(Color.RED);
        }else {
            txtcntdwn.setTextColor(defualtclr);
        }
    }

    private void finishQuiz() {
        Intent result = new Intent();
        result.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (bpt + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }else {
            Toast.makeText(this, "Press Back Again.!", Toast.LENGTH_SHORT).show();
        }
        bpt = System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!= null){
            countDownTimer.cancel();
        }else {

        }
    }
}

