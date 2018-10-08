package com.example.marlabs.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.support.annotation.Nullable;
import com.example.marlabs.quizapp.ContrastQuiz.*;

import java.util.ArrayList;
import java.util.List;

public class DBQuiz extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SAMPLE_QUIZ.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DBQuiz(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String Creat_QueTable = "CREATE TABLE " + QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUE + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_ANS_NO + " INTEGER " + ")";

        db.execSQL(Creat_QueTable);
        fillQueTable();
    }

        private void fillQueTable() {
        Questions q1 = new Questions("A is correct!", "A", "B", "C","1");
        addQue(q1);
        Questions q2 = new Questions("B is correct!", "A", "B", "C", "2");
        addQue(q2);
        Questions q3 = new Questions("C is correct!", "A", "B", "C", "3");
        addQue(q3);
        Questions q4 = new Questions("B is correct again!", "A", "B", "C", "2");
        addQue(q4);
        Questions q5 = new Questions("C is correct again!", "A", "B", "C", "3");
        addQue(q5);
    }



    public List<Questions> getAllQue() {
        List<Questions> questionsList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);

        if (cursor.moveToFirst()){
            do {
                Questions questions = new Questions();
                questions.setQue(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUE)));
                questions.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                questions.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                questions.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                questions.setAns_no(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANS_NO)));
                questionsList.add(questions);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;
    }

    private void addQue(Questions questions) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionTable.COLUMN_QUE, questions.getQue());
        contentValues.put(QuestionTable.COLUMN_OPTION1, questions.getOption1());
        contentValues.put(QuestionTable.COLUMN_OPTION2, questions.getOption2());
        contentValues.put(QuestionTable.COLUMN_OPTION3, questions.getOption3());
        contentValues.put(QuestionTable.COLUMN_ANS_NO, questions.getAns_no());

        db.insert(QuestionTable.TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }
}
