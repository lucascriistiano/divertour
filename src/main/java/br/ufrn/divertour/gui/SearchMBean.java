package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.divertour.model.Searchable;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;

@SessionScoped
@ManagedBean(name = "searchMBean")

public class SearchMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private GuideService guideService = GuideService.getInstance();
	private PlaceService placeService = PlaceService.getInstance();
	
	private Searchable selectedItem;
	
	List<Searchable> foundResults;
	
	public SearchMBean() {}
	
	public List<Searchable> findResults(String search) {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByNameSimilarity(search));
		foundResults.addAll(placeService.findByNameSimilarity(search));
		
		return foundResults;
	}
	
	public Searchable getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Searchable selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String showDetails() {
		if(this.selectedItem == null) {
			//TODO
			System.out.println("Error. Selected null value");
			return "";
		}
		
		return this.selectedItem.getDetailsPage();
	}
	
}
