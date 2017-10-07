package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Author: Chetan S.
 */


@DatabaseTable (tableName = "user_weight")
public class UserWeight {
    @DatabaseField(generatedId = true,columnName = "user_weight_id")
    private Integer id;

    @DatabaseField(columnName = "updated_on")
    private Date updatedOn;

    @DatabaseField(columnName = "weight_record")
    private int weight;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public UserWeight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public int compareTo(UserWeight weight) {
        if(weight.getUpdatedOn().before(this.getUpdatedOn())) {
            return 1;
        }

        if(weight.getUpdatedOn().after(this.getUpdatedOn())) {
            return -1;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
