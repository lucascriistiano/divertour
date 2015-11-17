package br.ufrn.divertour.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Place implements Searchable {

	@Id
	private String id;
	private String name;
	private String description;
	private City city;
	private String address;
	private float lat;
	private float lng;
	private String type;
	private List<String> categories;
	private List<String> images;
	private List<String> contacts;
	private String website;
	private int rating;
	private List<Comment> comments;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public List<String> getCategories() {
		return categories;
	}
	
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setCategory(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String getExhibitionName() {
		return this.getName() + " / " + Searchable.PLACE_RESULT; 
	}

	@Override
	public String getDetailsPage() {
		return Searchable.PLACE_DETAILS_PAGE;
	}

	@Override
	public String getConvertedId() {
		return Searchable.PLACE_FIRST_CHAR + this.getId();
	}
	
}