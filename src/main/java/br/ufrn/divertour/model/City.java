package br.ufrn.divertour.model;

import org.springframework.data.annotation.Id;

public class City {

	@Id
	private String id;
	private String name;
	private String state;
	
	public City() {}
	
	public City(String name, String state) {
		super();
		this.name = name;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}