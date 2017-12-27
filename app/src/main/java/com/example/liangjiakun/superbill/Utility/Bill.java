package com.example.liangjiakun.superbill.Utility;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liang Jiakun on 2017/5/1.
 */

public class Bill implements Serializable {
    private int out_in;
    private double money;
    private String kind;
    private String date;
    private String memo;

    public Bill() {
    }

    public Bill(int out_in, double money, String kind, String date, String memo) {
        this.out_in = out_in;
        this.money = money;
        this.kind = kind;
        this.date = date;
        this.memo = memo;
    }

    public int getOut_in() {
        return out_in;
    }

    public void setOut_in(int out_in) {
        this.out_in = out_in;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
