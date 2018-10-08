package com.example.marlabs.quizapp;

public class Questions {
    private String que;
    private String option1;
    private String option2;
    private String option3;
    public int ans_no;

    public Questions(String que, String a, String b, String c, String s){

    }

    public Questions(String que, String option1, String option2, String option3, int ans_no) {
        this.que = que;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.ans_no = ans_no;
    }

    public Questions() {

    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAns_no() {
        return ans_no;
    }

    public void setAns_no(int ans_no) {
        this.ans_no = ans_no;
    }
}


