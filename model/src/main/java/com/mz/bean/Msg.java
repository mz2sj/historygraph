package com.mz.bean;

import java.util.HashMap;
import java.util.Map;

public class Msg {
//    private int code;
    private String msg;
    private Map<String, Object> data = new HashMap<String, Object>();

    public Msg() {
    }

    public static Msg success() {
        Msg result = new Msg();
//        result.setCode(100);
        result.setMsg("处理成功");
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
//        result.setCode(200);
        result.setMsg("处理失败");
        return result;
    }

    public Msg add(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

//    public int getCode() {
//        return this.code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
