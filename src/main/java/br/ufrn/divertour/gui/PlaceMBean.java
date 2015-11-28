package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.exception.ValidationException;

@ViewScoped
@ManagedBean(name = "placeMBean")
public class PlaceMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Place place;
	private PlaceService placeService = PlaceService.getInstance();
	private CityService cityService = CityService.getInstance();

	private float currentLat;
    private float currentLng;

    private String insertedContact;
    
    private UploadedFile selectedImageFile;
    private List<UploadedFile> selectedImagesFiles;

    private List<String> insertedContacts;
//    private List<String> uploadedImagesPath;
    
	private List<String> typesOfPlace;
	private List<String> categoriesOfPlace;
	
	public PlaceMBean() {
		this.place = new Place();
		
		this.insertedContacts = new ArrayList<>();
//		this.uploadedImagesPath = new ArrayList<>();
		this.selectedImagesFiles = new ArrayList<>();
		
		this.typesOfPlace = PlaceService.getTypesOfPlace();
		this.categoriesOfPlace = PlaceService.getCategoriesOfPlace();
	}
	
    public void addContact() {
    	if(insertedContact != null && !insertedContact.equals("")) {
    		this.insertedContacts.add(insertedContact);
    	} else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Você deve inserir um contato para adicioná-lo"));
//    		return "";
    	}
    }
    
    public void addImage() {
    	if(this.selectedImageFile != null) {
        	System.out.println("Adicionando o arquivo " + selectedImageFile.getFileName());
    		this.selectedImagesFiles.add(selectedImageFile);
    		this.selectedImageFile = null;
    		System.out.println("Arquivo adicionado");
    	} else {
    		System.out.println("Arquivo não adicionado");
    	}
    }
    
    public void cancelImage() {
    	this.selectedImageFile = null;
    }
    
    public String register() {
		try {
			this.place.setContacts(insertedContacts);
//			this.place.setImages(uploadedImagesPath);
			
			this.placeService.register(this.place);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			return "/pages/restricted/homepage";
		} catch (ValidationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
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
	
	public UploadedFile getSelectedImageFile() {
		return selectedImageFile;
	}

	public void setSelectedImageFile(UploadedFile selectedImageFile) {
		System.out.println("Setou");
		this.selectedImageFile = selectedImageFile;
	}
	
	public List<UploadedFile> getSelectedImagesFiles() {
		return selectedImagesFiles;
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
