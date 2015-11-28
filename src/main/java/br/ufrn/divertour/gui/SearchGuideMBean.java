package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.service.GuideService;

@ViewScoped
@ManagedBean(name = "searchGuideMBean")
public class SearchGuideMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final GuideService guideService;
	
	private List<Guide> foundResults;

	private Map<String, Map<String, String>> searchFilters = new HashMap<String, Map<String,String>>();

	private Map<String, String> searchFiltersNames;
	private Map<String, String> searchFiltersValues;
	
	private String selectedFilterName;
	private String selectedFilterValue;
	
	public SearchGuideMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.guideService = (GuideService) context.getBean(GuideService.class);
		
		this.foundResults = guideService.listAll();
	}
	
	@PostConstruct
    public void init() {
		this.searchFiltersNames = new HashMap<>();
		this.searchFiltersNames.put("Período", "period");
		this.searchFiltersNames.put("Número de Lugares", "numberOfPlaces");
		this.searchFiltersNames.put("Categoria", "category");
		
		// Load periods
		Map<String,String> map = new HashMap<String, String>();
		List<Integer> periods = guideService.getPeriodsOfGuide();
		Collections.sort(periods);
		for (Integer period : periods) {
			String strPeriod = String.valueOf(period);
			map.put(strPeriod, strPeriod);
		}
		this.searchFilters.put("period", map);
		
		// Load number of places
		map = new HashMap<String, String>();
		List<Integer> numberOfPlaces = guideService.getNumberOfPlaces();
		for (Integer places : numberOfPlaces) {
			String strNumPlaces = String.valueOf(places);
			map.put(strNumPlaces, strNumPlaces);
		}
		this.searchFilters.put("numberOfPlaces", map);

		// Load categories
		map = new HashMap<String, String>();
		List<String> categories = GuideService.getCategoriesOfGuide();
		for (String category : categories) {
			map.put(category, category);
		}
		this.searchFilters.put("category", map);
	}
	
	public List<Guide> getFoundResults() {
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
			foundResults = guideService.listAll();
		}
	}
	
	public void onFilterValueChange() {
		if(selectedFilterValue != null && !selectedFilterValue.equals("")) {
			if(selectedFilterName.equals("period")) {
				int period = Integer.parseInt(selectedFilterValue);
				foundResults = guideService.findByPeriod(period);
			} else if(selectedFilterName.equals("numberOfPlaces")) {
				int numberOfPlaces = Integer.parseInt(selectedFilterValue);
				foundResults = guideService.findByNumberOfPlaces(numberOfPlaces);
			} else if(selectedFilterName.equals("category")) {
				foundResults = guideService.findByCategory(selectedFilterValue);
			} else {
				foundResults = guideService.listAll();
			}
		} else {
			foundResults = guideService.listAll();
		}
	}
	
	public String showDetails(String id) {
		return "/pages/common/guides/details.xhtml?id=" + id + "&faces-redirect=true";
	}
	
	public String customize(String id) {
		System.out.println("Vai personalizar a partir da rota o lugar com ID: " + id);
		return "";
	}
	
	public String newGuide() {
		return "pretty:guide_register";
	}
	
}
