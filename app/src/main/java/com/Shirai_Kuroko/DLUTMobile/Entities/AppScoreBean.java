package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.*;

public class AppScoreBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private float average;
    private int times;
    private int total;

    public float getAverage() {
        return this.average;
    }

    public int getTimes() {
        return this.times;
    }

    public int getTotal() {
        return this.total;
    }

    public void setAverage(final float average) {
        this.average = average;
    }

    public void setTimes(final int times) {
        this.times = times;
    }

    public void setTotal(final int total) {
        this.total = total;
    }
}
