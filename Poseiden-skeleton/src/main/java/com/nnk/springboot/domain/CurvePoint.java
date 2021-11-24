package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.sql.Timestamp;

@Entity
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Positive(message = "Must not be null")
    @Column(name = "curve_id")
    private int curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public CurvePoint() {

    }

    public CurvePoint(int id, @NotNull(message = "Must not be null") int curveId, Timestamp asOfDate, Double term,
	    Double value, Timestamp creationDate) {

	this.id = id;
	this.curveId = curveId;
	this.asOfDate = asOfDate;
	this.term = term;
	this.value = value;
	this.creationDate = creationDate;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getCurveId() {
	return curveId;
    }

    public void setCurveId(int curveId) {
	this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
	return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
	this.asOfDate = asOfDate;
    }

    public Double getTerm() {
	return term;
    }

    public void setTerm(Double term) {
	this.term = term;
    }

    public Double getValue() {
	return value;
    }

    public void setValue(Double value) {
	this.value = value;
    }

    public Timestamp getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
	this.creationDate = creationDate;
    }

}
