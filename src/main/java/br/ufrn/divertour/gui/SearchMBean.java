package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.event.SelectEvent;

import br.ufrn.divertour.model.Searchable;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;

@ManagedBean(name = "searchMBean")
public class SearchMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private GuideService guideService = GuideService.getInstance();
	private PlaceService placeService = PlaceService.getInstance();
	
	private String selectedItemId;
	
	public SearchMBean() {}
	
	public List<Searchable> findResults(String search) {
		List<Searchable> foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByNameSimilarity(search));
		foundResults.addAll(placeService.findByNameSimilarity(search));
		
		return foundResults;
	}
	
	public void onItemSelect(SelectEvent event) {
		System.out.println("onItemSelect");
		if(selectedItemId != null) {
			System.out.println("Selecionado: " + selectedItemId);
		}
	}
	
	public void detailSelected() {
		System.out.println("detailSelected");
		if(selectedItemId != null) {
			System.out.println("Selecionado: " + selectedItemId);
		}
	}
	
	public String getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}
	
}
