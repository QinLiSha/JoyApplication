package com.example.administrator.joyapplication.model.model;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ForgetPassword {
    private int result;
    private String explain;

    public ForgetPassword(int result, String explain) {
        this.result = result;
        this.explain = explain;
    }

    public ForgetPassword() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "ForgetPassword{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                '}';
    }
}
