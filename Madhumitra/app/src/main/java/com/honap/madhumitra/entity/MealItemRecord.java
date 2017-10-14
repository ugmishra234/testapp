package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Author: Chetan S.
 */

@DatabaseTable(tableName = "meal_item_record")
public class MealItemRecord implements Serializable{
    @DatabaseField(generatedId = true,columnName = "meal_item_record_id")
    private Integer id;

    @DatabaseField(columnName = "meal_item")
    private String mealItem;

    @DatabaseField(columnName = "no_dishes")
    private int numDishes;

    @DatabaseField(columnName = "inverse_meal_record", foreign = true, foreignAutoRefresh = true)
    private MealRecord mealRecord;

    @DatabaseField(columnName = "calorie_intake")
    private int calorieIntake;



    public MealItemRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMealItem() {
        return mealItem;
    }

    public void setMealItem(String mealItem) {
        this.mealItem = mealItem;
    }

    public int getNumDishes() {
        return numDishes;
    }

    public void setNumDishes(int numDishes) {
        this.numDishes = numDishes;
    }

    public MealRecord getMealRecord() {
        return mealRecord;
    }

    public void setMealRecord(MealRecord mealRecord) {
        this.mealRecord = mealRecord;
    }

    public int getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(int calorieIntake) {
        this.calorieIntake = calorieIntake;
    }
}
