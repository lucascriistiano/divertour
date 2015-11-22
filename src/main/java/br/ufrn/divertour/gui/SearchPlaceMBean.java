package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.SearchFilter;
import br.ufrn.divertour.service.PlaceService;

@SessionScoped
@ManagedBean(name = "searchPlaceMBean")

public class SearchPlaceMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PlaceService placeService = PlaceService.getInstance();
	
	private SearchFilter selectedFilter;
	private String selectedFilterValue;
	
	List<Place> foundResults;	
	
	public SearchPlaceMBean() {}
	

	public List<Place> getFoundResults() {
//		return foundResults;
		return placeService.listAll();
	}

	public void setFoundResults(List<Place> foundResults) {
		this.foundResults = foundResults;
	}
	
//	public List<Place> findResultsByCity(String city) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByCity(city));
//		
//		return foundResults;
//	}
//	
//	public List<Place> findResultsByType(String type) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByType(type));
//		
//		return foundResults;
//	}
//	
//	public List<Place> findResultsByCategory(String category) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByType(category));
//		
//		return foundResults;
//	}
//	
//	
//	public List<Place> findResultsByCityAndType(String city, String type) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByCityAndType(city, type));
//		
//		return foundResults;
//	}
//	
//	public List<Place> findResultsByCityAndCategory(String city, String category) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByCityAndCategory(city, category));
//		
//		return foundResults;
//	}
//	
//	public List<Place> findResultsByTypeAndCategory(String type, String category) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByTypeAndCategory(type, category));
//		
//		return foundResults;
//	}
//	
//	public List<Place> findResults(String city, String type, String category) {
//		foundResults = new ArrayList<>();
//		foundResults.addAll(placeService.findByCityAndTypeAndCategory(city, type, category));
//		
//		return foundResults;
//	}
	
	public SearchFilter getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(SearchFilter selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	
	public String getSelectedFilterValue() {
		return selectedFilterValue;
	}

	public void setSelectedFilterValue(String selectedFilterValue) {
		this.selectedFilterValue = selectedFilterValue;
	}

	public List<SearchFilter> getFilters() {
		List<SearchFilter> searchFilters = new ArrayList<>();
		searchFilters.add(new SearchFilter("Nenhum", "", Arrays.asList()));
		searchFilters.add(new SearchFilter("Categoria", "category", Arrays.asList("1 estrela", "2 estrelas", "3 estrelas", "4 estrelas", "5 estrelas")));
		searchFilters.add(new SearchFilter("Tipo", "type", Arrays.asList("Hotel", "Ponto Turístico", "Restaurante", "Loja", "Outro")));
		searchFilters.add(new SearchFilter("Cidade", "city", Arrays.asList("Natal/RN", "São Paulo/SP")));
		return searchFilters;
	}
	
	public List<String> getValues() {
		if(selectedFilter != null) return selectedFilter.getPossibleValues();
		return new ArrayList<>();
	}
	
	public void test() {
		System.out.println("teste");
	}
	
	public String showDetails(String id) {
		System.out.println("Vai detalhar lugar com ID: " + id);
		return "";
	}
	
	public String addToRoute(String id) {
		System.out.println("Vai adicionar a uma rota o lugar com ID: " + id);
		return "";
	}
	
}
