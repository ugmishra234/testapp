package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Author: Chetan S.
 */
@DatabaseTable(tableName = "home_investigation_para_value")
public class HomeInvestigationParamValue {
    @DatabaseField(generatedId = true,columnName = "home_investigation_para_value_id")
    private Integer id;

    @DatabaseField(columnName = "param")
    private String parameter;

    @DatabaseField(columnName = "param_value")
    private String value;

    @DatabaseField(columnName = "inverse_record",foreign = true, foreignAutoRefresh = true)
    private HomeInvestigationRecord record;

    public HomeInvestigationParamValue() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HomeInvestigationRecord getRecord() {
        return record;
    }

    public void setRecord(HomeInvestigationRecord record) {
        this.record = record;
    }
}
