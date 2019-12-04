package com.github.snakerflow.result;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 5079091973335356739L;

    //返回码
    private String code = "0";

    //返回信息
    private String msg;

    private boolean success = false;

    //结果数据
    private T data;

    public Result<T> buildResult(String code, String msg, boolean success, T data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setSuccess(success);
        this.setData(data);
        return this;
    }

    public Result<T> buildSuccess(T data) {
        buildResult("0", "", true, data);
        return this;
    }

    public Result<T> buildFailue() {
        buildResult("1", "服务器出错", false, null);
        return this;
    }

    public Result<T> buildFailue(String errorCode, String msg) {
        buildResult(errorCode, msg, false, null);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
