package br.ufrn.divertour.model;

public class SearchFilter {

	private String filterDisplayName;
	private String filterName;
	
	public SearchFilter(String filterDisplayName, String filterName) {
		this.filterDisplayName = filterDisplayName;
		this.filterName = filterName;
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
	
}
