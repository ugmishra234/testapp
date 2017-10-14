package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Author: Chetan S.
 */

@DatabaseTable (tableName = "exercise")
public class Exercise {
    @DatabaseField(generatedId = true,columnName = "exercise_id")
    private Integer id;

    @DatabaseField(columnName = "exercise_name")
    private String name;

    @DatabaseField(columnName = "cal_consumption_min")
    private double caloricConsumptionPerMin;


    public Exercise() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCaloricConsumptionPerMin() {
        return caloricConsumptionPerMin;
    }

    public void setCaloricConsumptionPerMin(double caloricConsumptionPerMin) {
        this.caloricConsumptionPerMin = caloricConsumptionPerMin;
    }
}
