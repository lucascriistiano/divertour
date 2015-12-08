package br.ufrn.divertour.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.PlaceService;

@FacesConverter("placeConverter")
public class PlaceConverter implements Converter {
	
	private PlaceService placeService;
	
	public PlaceConverter() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
	}
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
			String id = value;
			return placeService.findById(id);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if(object != null) {
			try {
				return ((Place) object).getId();
			} catch (Exception e) {
				return null;
			}
        }
		return null;
	}

}
