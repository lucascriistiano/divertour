package br.ufrn.divertour.model;

import java.util.Date;

public class Comment {
	
	private String title;
	private String text;
	private Date date;
	private int rating;
	private String userId;

	public Comment() {}
	
	public Comment(String title, String text, Date date, int rating, String userId) {
		super();
		this.title = title;
		this.text = text;
		this.date = date;
		this.rating = rating;
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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