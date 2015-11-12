package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.exception.ValidationException;

@ManagedBean(name = "cityMBean")
@ViewScoped
public class CityMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private CityService cityService = CityService.getInstance();
	
	private City city;
	
	public CityMBean() {
		this.city = new City();
	}
	
	public String register() {
		try {
			this.cityService.register(this.city);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			return "/pages/admin/cities/list";
		} catch (ValidationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String edit(String id) {
		System.out.println("Edit city with id = " + id);
		return "";
	}
	
	public String remove(String id) {
		this.cityService.remove(id);
		return "";
	}

	public City getCity() {
		return city;
	}

	public void setUser(City city) {
		this.city = city;
	}

	public List<City> getCities() {
		return cityService.listAll();
	}
	
	public List<String> getStates() {
		return CityService.getStates();
	}
	
}
