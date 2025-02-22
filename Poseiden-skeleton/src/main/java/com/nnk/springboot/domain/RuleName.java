package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rule_name")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Column(name = "json")
    private String json;

    @Column(name = "template")
    private String template;

    @Column(name = "sql_str")
    private String sqlStr;

    @Column(name = "sql_part")
    private String sqlPart;

    public RuleName() {

    }

    public RuleName(int id, String name, String description, String json, String template, String sqlStr,
	    String sqlPart) {

	this.id = id;
	this.name = name;
	this.description = description;
	this.json = json;
	this.template = template;
	this.sqlStr = sqlStr;
	this.sqlPart = sqlPart;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getJson() {
	return json;
    }

    public void setJson(String json) {
	this.json = json;
    }

    public String getTemplate() {
	return template;
    }

    public void setTemplate(String template) {
	this.template = template;
    }

    public String getSqlStr() {
	return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
	this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
	return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
	this.sqlPart = sqlPart;
    }

}
