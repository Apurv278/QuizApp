package com.example.marlabs.quizapp;

import android.provider.BaseColumns;

public final class ContrastQuiz {

    private ContrastQuiz(){

    }

    public static class QuestionTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_que";
        public static final String COLUMN_QUE = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANS_NO = "ans_no";
    }
}
