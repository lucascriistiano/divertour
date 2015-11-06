package br.ufrn.divertour.model;

import org.springframework.data.annotation.Id;

public class Route {

	@Id
	private String id;
	
	private String name;
	private String state;
	private float lat;
	private float lon;

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

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

}