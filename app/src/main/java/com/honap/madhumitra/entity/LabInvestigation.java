package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Chetan S.
 */
@DatabaseTable(tableName = "lab_investigation")
public class LabInvestigation implements Serializable {

    @DatabaseField(generatedId = true,columnName = "lab_investigation_id")
    private Integer id;

    @DatabaseField(columnName = "investigation_name")
    private String name;

    public LabInvestigation() {
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
}
