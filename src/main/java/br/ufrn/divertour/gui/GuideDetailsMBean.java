package br.ufrn.divertour.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.UserService;

@ManagedBean(name = "guideDetailsMBean")
@ViewScoped
public class GuideDetailsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private GuideService guideService;
	
	private String guideId;
	
	@ManagedProperty(value="#{searchMBean.selectedItem}")
	private Guide selectedGuide;
	
	public GuideDetailsMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.guideService = (GuideService) context.getBean(GuideService.class);			
	}
		
	
	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public Guide getSelectedGuide() {
		return selectedGuide;
	}

	public void setSelectedGuide(Guide selectedGuide) {
		this.selectedGuide = selectedGuide;
	}	
	
	public void loadData() {
		//Load selected guide info
		this.selectedGuide = guideService.findById(guideId);
	}
	
}
