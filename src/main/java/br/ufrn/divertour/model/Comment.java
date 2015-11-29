package br.ufrn.divertour.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Comment {
	
	@Id
	private String id;
	private String text;
	private int rating;
	private Date date;
	private String userId;

	public Comment() {}
	
	public Comment(String text, Date date, int rating, String userId) {
		super();
		this.text = text;
		this.date = date;
		this.rating = rating;
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}