package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.service.GuideService;


@SessionScoped
@ManagedBean(name = "searchGuideMBean")

public class SearchGuideMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private GuideService guideService = GuideService.getInstance();
	
	private int period;
	
	private int numberOfPlaces;
	
	private String category;
	
	List<Guide> foundResults;	
	
	public SearchGuideMBean() {}
	

	public List<Guide> getFoundResults() {
		return foundResults;
	}


	public void setFoundResults(List<Guide> foundResults) {
		this.foundResults = foundResults;
	}

		

	public int getPeriod() {
		return period;
	}


	public void setPeriod(int period) {
		this.period = period;
	}


	public int getNumberOfPlaces() {
		return numberOfPlaces;
	}


	public void setNumberOfPlaces(int numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public List<Guide> findResultsByNumberOfPlaces(int numberOfPlaces) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByNumberOfPlaces(numberOfPlaces));
		
		return foundResults;
	}
	
	public List<Guide> findResultsByPerior(int period) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByPeriod(period));
		
		return foundResults;
	}
	
	public List<Guide> findResultsByCategory(String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByCategory(category));
		
		return foundResults;
	}
	
	
	public List<Guide> findResultsByNumberOfPlacesAndPerior(int numberOfPlaces, int period) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByNumberOfPlacesAndPeriod(numberOfPlaces, period));
		
		return foundResults;
	}
	
	public List<Guide> findResultsByNumberOfPlacesAndCategory(int numberOfPlaces, String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByNumberOfPlacesAndCategory(numberOfPlaces, category));
		
		return foundResults;
	}
	
	public List<Guide> findResultsByPeriorAndCategory(int period, String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByPeriodAndCategory(period, category));
		
		return foundResults;
	}
	
	public List<Guide> findResults(int numberOfPlaces, int period, String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByNumberOfPlacesAndPeriodAndCategory(numberOfPlaces, period, category));
		
		return foundResults;
	}
	
	
//	public String showDetails() {
//		if(this.selectedItem == null) {
//			//TODO
//			System.out.println("Error. Selected null value");
//			return "";
//		}
//		
//		return this.selectedItem.getDetailsPage();
//	}
	
}
