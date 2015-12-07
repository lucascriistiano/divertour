package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.exception.PhotoSavingException;
import br.ufrn.divertour.service.exception.ValidationException;

@ViewScoped
@ManagedBean(name = "placeMBean")
public class PlaceMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final PlaceService placeService;	
	private final CityService cityService;

	private Place place;
	
	private float currentLat;
    private float currentLng;

    private String insertedContact;
    
    private List<UploadedFile> uploadedImages;
    private List<String> insertedContacts;
    
	private final List<String> typesOfPlace;
	private final List<String> categoriesOfPlace;
	
	public PlaceMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.cityService = (CityService) context.getBean(CityService.class);
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
		
		this.place = new Place();
		
		this.insertedContacts = new ArrayList<>();
		this.uploadedImages = new ArrayList<>();
		
		this.typesOfPlace = PlaceService.getTypesOfPlace();
		this.categoriesOfPlace = PlaceService.getCategoriesOfPlace();
	}
	
    public void addContact() {
    	if(insertedContact != null && !insertedContact.equals("")) {
    		this.insertedContacts.add(insertedContact);
    	} else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Você deve inserir um contato para adicioná-lo"));
    	}
    }
    
//    public void discardImage() {
//    	this.selectedImageFile = null;
//    }
    
    public void upload(FileUploadEvent event) {
    	UploadedFile uploadedImage = event.getFile();
    	this.uploadedImages.add(uploadedImage);
    	System.out.println("Imagem adicionada: " + uploadedImage.getFileName());
    }
    
    public String register() {
		try {
			this.place.setContacts(insertedContacts);
			this.placeService.register(this.place);
			
			if(this.uploadedImages != null) {
				this.placeService.savePlacePhotos(place, uploadedImages);
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			return "/pages/restricted/homepage";
		} catch (ValidationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (PhotoSavingException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", e.getMessage()));
			return "/pages/restricted/homepage";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "";
	}

	public String edit(String id) {
		System.out.println("Edit place with id = " + id);
		return "";
	}
	
	public String remove(String id) {
		this.placeService.remove(id);
		return "";
	}
	
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
	
	public List<UploadedFile> getUploadedImages() {
		return uploadedImages;
	}

	public float getCurrentLat() {
		return currentLat;
	}

	public void setCurrentLat(float currentLat) {
		this.currentLat = currentLat;
	}

	public float getCurrentLng() {
		return currentLng;
	}

	public void setCurrentLng(float currentLng) {
		this.currentLng = currentLng;
	}

	public List<Place> getPlaces() {
		return placeService.listAll();
	}

	public List<String> getTypesOfPlace() {
		return typesOfPlace;
	}

	public List<String> getCategoriesOfPlace() {
		return categoriesOfPlace;
	}
	
	public List<City> getCities() {
		return cityService.listAll();
	}
	
	public String getInsertedContact() {
		return insertedContact;
	}
	
	public void setInsertedContact(String insertedContact) {
		this.insertedContact = insertedContact;
	}

	public List<String> getInsertedContacts() {
		return insertedContacts;
	}
	
}
