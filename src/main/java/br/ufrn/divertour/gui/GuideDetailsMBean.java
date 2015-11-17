package br.ufrn.divertour.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.ufrn.divertour.model.Guide;

@ManagedBean(name = "guideDetailsMBean")
@ViewScoped
public class GuideDetailsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{searchMBean.selectedItem}")
	private Guide selectedGuide;
	
	public GuideDetailsMBean() {}
	
	public Guide getSelectedGuide() {
		return selectedGuide;
	}

	public void setSelectedGuide(Guide selectedGuide) {
		this.selectedGuide = selectedGuide;
	}
	
}
