package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Searchable;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;

@ViewScoped
@ManagedBean(name = "indexListMBean")
public class IndexListMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final GuideService guideService;
	private final PlaceService placeService;
	
	private List<Searchable> foundResults;
	
	public IndexListMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.guideService = (GuideService) context.getBean(GuideService.class);
		this.placeService = (PlaceService) context.getBean(PlaceService.class);

		loadItems();
	}
	
	public void loadItems() {
		foundResults = new ArrayList<>();
		foundResults.addAll(guideService.findByCityAndState("Natal", "RN"));
		foundResults.addAll(placeService.findByCityAndState("Natal", "RN"));
	}
	
	public List<Searchable> getFoundResults() {
		return foundResults;
	}

	public String showDetails() {
//		if(this.selectedItem == null) {
//			//TODO show error message
//			System.out.println("Error. Selected null value");
//			return "";
//		}
//		
//		return this.selectedItem.getDetailsPage();
		return "";
	}
	
}
