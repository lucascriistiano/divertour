package br.ufrn.divertour.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Searchable;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;

@FacesConverter("searchableConverter")
public class SearchableConverter implements Converter {
	
	private PlaceService placeService;
	private GuideService guideService;
	
	public SearchableConverter() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
		this.guideService = (GuideService) context.getBean(GuideService.class);
	}
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
			char firstChar = value.charAt(0);
			String id = value.substring(1);
			
			if(firstChar == Searchable.PLACE_FIRST_CHAR) {
				return placeService.findById(id);
			} else if(firstChar == Searchable.GUIDE_FIRST_CHAR) {
				return guideService.findById(id);
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if(object != null) {
			try {
				return ((Searchable) object).getConvertedId();
			} catch (Exception e) {
				return null;
			}
        }
		return null;
	}

}
