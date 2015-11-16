package br.ufrn.divertour.model;

public interface Searchable {

	public static final String PLACE_RESULT = "Lugar";
	public static final String GUIDE_RESULT = "Roteiro";
	
//	public static final String PLACE_DETAILS_PAGE = "/divertour/";
//	public static final String GUIDE_DETAILS_PAGE = "Roteiro";
	
	
	public String getId();
	public String getExhibitionName();
//	public String getDetailsPage();
	
}
