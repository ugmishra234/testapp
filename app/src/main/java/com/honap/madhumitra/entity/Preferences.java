package com.honap.madhumitra.entity;

import java.io.Serializable;

/**
 * Author: Chetan S.
 */
public class Preferences implements Serializable{
    private int mornHour;
    private int mornMin;
    private int noonHour;
    private int noonMin;
    private int evenHour;
    private int evenMin;
    private int nightHour;
    private int nightMin;

    public Preferences() {
    }

    public int getMornHour() {
        return mornHour;
    }

    public void setMornHour(int mornHour) {
        this.mornHour = mornHour;
    }

    public int getMornMin() {
        return mornMin;
    }

    public void setMornMin(int mornMin) {
        this.mornMin = mornMin;
    }

    public int getNoonHour() {
        return noonHour;
    }

    public void setNoonHour(int noonHour) {
        this.noonHour = noonHour;
    }

    public int getNoonMin() {
        return noonMin;
    }

    public void setNoonMin(int noonMin) {
        this.noonMin = noonMin;
    }

    public int getEvenHour() {
        return evenHour;
    }

    public void setEvenHour(int evenHour) {
        this.evenHour = evenHour;
    }

    public int getEvenMin() {
        return evenMin;
    }

    public void setEvenMin(int evenMin) {
        this.evenMin = evenMin;
    }

    public int getNightHour() {
        return nightHour;
    }

    public void setNightHour(int nightHour) {
        this.nightHour = nightHour;
    }

    public int getNightMin() {
        return nightMin;
    }

    public void setNightMin(int nightMin) {
        this.nightMin = nightMin;
    }
}
