package com.example.distributedsystems.model;

import java.util.Date;

public class Diabetic {
    int uid;
    double gluc;
    double carbs;
    double med_dose;
    Date date;

    public Diabetic(int uid, double gluc, double carbs, double med_dose, Date date) {
        this.uid = uid;
        this.gluc = gluc;
        this.carbs = carbs;
        this.med_dose = med_dose;
        this.date = date;
    }

    public Diabetic() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getGluc() {
        return gluc;
    }

    public void setGluc(double gluc) {
        this.gluc = gluc;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getMed_dose() {
        return med_dose;
    }

    public void setMed_dose(double med_dose) {
        this.med_dose = med_dose;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
