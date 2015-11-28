package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.PlaceService;

@ViewScoped
@ManagedBean(name = "searchPlaceMBean")
public class SearchPlaceMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final PlaceService placeService;
	private final CityService cityService;
	
	private List<Place> foundResults;

	private Map<String, Map<String, String>> searchFilters = new HashMap<String, Map<String,String>>();

	private Map<String, String> searchFiltersNames;
	private Map<String, String> searchFiltersValues;
	
	private String selectedFilterName;
	private String selectedFilterValue;
	
	public SearchPlaceMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
		this.cityService = (CityService) context.getBean(CityService.class);
		
		this.foundResults = placeService.listAll();
	}
	
	@PostConstruct
    public void init() {		
		this.searchFiltersNames = new HashMap<>();
		this.searchFiltersNames.put("Categoria", "category");
		this.searchFiltersNames.put("Tipo", "type");
		this.searchFiltersNames.put("Cidade", "city");
		
		// Load categories
		Map<String,String> map = new HashMap<String, String>();
		List<String> categoriesOfPlace = PlaceService.getCategoriesOfPlace();
		for (String category : categoriesOfPlace) {
			map.put(category, category);
		}
		this.searchFilters.put("category", map);
		
		// Load types
		map = new HashMap<String, String>();
		List<String> typesOfPlace = PlaceService.getTypesOfPlace();
		for (String type : typesOfPlace) {
			map.put(type, type);
		}
		this.searchFilters.put("type", map);

		// Load cities
		map = new HashMap<String, String>();
		List<City> cities = cityService.listAll();
		for (City city : cities) {
			String cityFullName = city.getName() + "/" + city.getState();
			map.put(cityFullName, cityFullName);
		}
		this.searchFilters.put("city", map);
	}

	public List<Place> getFoundResults() {
		return foundResults;
	}
	
	public String getSelectedFilterName() {
		return selectedFilterName;
	}

	public void setSelectedFilterName(String selectedFilterName) {
		this.selectedFilterName = selectedFilterName;
	}
	
	public String getSelectedFilterValue() {
		return selectedFilterValue;
	}

	public void setSelectedFilterValue(String selectedFilterValue) {
		this.selectedFilterValue = selectedFilterValue;
	}

	public Map<String, String> getFiltersNames() {
		return this.searchFiltersNames;
	}
	
	public Map<String, String> getFiltersValues() {
		return this.searchFiltersValues;
	}
	
	public void onFilterNameChange() {
		if(selectedFilterName != null && !selectedFilterName.equals("")) {
            searchFiltersValues = searchFilters.get(selectedFilterName);
		} else {
			searchFiltersValues = new HashMap<String, String>();
			foundResults = placeService.listAll();
		}
	}
	
	public void onFilterValueChange() {
		if(selectedFilterValue != null && !selectedFilterValue.equals("")) {
			if(selectedFilterName.equals("type")) {
				foundResults = placeService.findByType(selectedFilterValue);
			} else if(selectedFilterName.equals("category")) {
				foundResults = placeService.findByCategory(selectedFilterValue);
			} else if(selectedFilterName.equals("city")) {
				String[] cityFields = selectedFilterValue.split("/");
				String cityName = cityFields[0];
				String cityState = cityFields[1];
				foundResults = placeService.findByCityAndState(cityName, cityState);
			} else {
				foundResults = placeService.listAll();
			}
		} else {
			foundResults = placeService.listAll();
		}
	}
	
	public String showDetails(String id) {
		return "/pages/common/places/details.xhtml?id=" + id + "&faces-redirect=true";
	}
	
	public String addToRoute(String id) {
		System.out.println("Vai adicionar a uma rota o lugar com ID: " + id);
		return "";
	}
	
	public String newPlace() {
		return "pretty:place_register";
	}
	
}
