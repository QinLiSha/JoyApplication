package com.example.administrator.joyapplication.model.model;

/**
 * Created by Administrator on 2016/9/6.
 */
public class BaseEntry <T>{
    private String message;
    private int status;
    private T data;//可能是集合也可能是对象

    public BaseEntry(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
