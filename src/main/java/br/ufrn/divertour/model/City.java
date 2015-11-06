package br.ufrn.divertour.model;

import java.util.Calendar;

import org.springframework.data.annotation.Id;

public class City {

	@Id
	private String id;
	
	private String name;
	private String category;
	private Calendar creationDate;
	private int period; // days to complete route

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}