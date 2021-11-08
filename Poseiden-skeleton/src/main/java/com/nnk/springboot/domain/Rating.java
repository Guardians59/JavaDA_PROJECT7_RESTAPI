package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "moodys_rating")
    private String moodysRating;

    @Column(name = "sand_p_rating")
    private String sandPRating;

    @Column(name = "fitch_rating")
    private String fitchRating;

    @Column(name = "order_number")
    private int orderNumber;

    public Rating() {

    }

    public Rating(int id, String moodysRating, String sandPRating, String fitchRating, int orderNumber) {

	this.id = id;
	this.moodysRating = moodysRating;
	this.sandPRating = sandPRating;
	this.fitchRating = fitchRating;
	this.orderNumber = orderNumber;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getMoodysRating() {
	return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
	this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
	return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
	this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
	return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
	this.fitchRating = fitchRating;
    }

    public int getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
	this.orderNumber = orderNumber;
    }

}
