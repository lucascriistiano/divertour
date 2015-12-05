package br.ufrn.divertour.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Guide implements Searchable {

	@Id
	private String id;
	private String name;
	private String category;
	private Date creationDate;
	private int period;
	private int rating;
	private List<Comment> comments;
	private List<Place> places;

	public Guide() {}
	
	public Guide(String name, String category, Date creationDate, int period, int rating, List<Comment> comments, List<Place> places) {
		super();
		this.name = name;
		this.category = category;
		this.creationDate = creationDate;
		this.period = period;
		this.rating = rating;
		this.comments = comments;
		this.places = places;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
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

	public void setPeriod(int period) {
		this.period = period;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	@Override
	public String getExhibitionName() {
		return this.getName() + " / " + Searchable.GUIDE_RESULT; 
	}

	@Override
	public String getDetailsPage() {
		return Searchable.GUIDE_DETAILS_PAGE + "?id=" + this.id + "&faces-redirect=true";
	}

	@Override
	public String getConvertedId() {
		return Searchable.GUIDE_FIRST_CHAR + this.getId();
	}

	@Override
	public String getSearchableType() {
		return Searchable.GUIDE_RESULT;
	}
	
}