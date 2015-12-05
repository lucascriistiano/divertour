package br.ufrn.divertour.model;

public interface Searchable {

	public static final String PLACE_RESULT = "Lugar";
	public static final String GUIDE_RESULT = "Roteiro";
	
	public static final String PLACE_DETAILS_PAGE = "/pages/common/places/details.xhtml";
	public static final String GUIDE_DETAILS_PAGE = "/pages/common/guides/details.xhtml";
	
	public static final char PLACE_FIRST_CHAR = 'P';
	public static final char GUIDE_FIRST_CHAR = 'G';
	
	public String getId();
	public String getName();
	public String getSearchableType();
	public String getExhibitionName();
	public int getRating();
	public String getDetailsPage();
	public String getConvertedId();
	
}
