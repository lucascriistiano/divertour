package br.ufrn.divertour.model;

import java.util.List;

public class SearchFilter {

	private String filterDisplayName;
	private String filterName;
	private List<String> possibleValues;
	
	public SearchFilter(String filterDisplayName, String filterName, List<String> possibleValues) {
		this.filterDisplayName = filterDisplayName;
		this.filterName = filterName;
		this.possibleValues = possibleValues;
	}

	public String getFilterDisplayName() {
		return filterDisplayName;
	}

	public void setFilterDisplayName(String filterDisplayName) {
		this.filterDisplayName = filterDisplayName;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}
	
}
