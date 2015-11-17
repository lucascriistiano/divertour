package br.ufrn.divertour.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

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
	
	private MapModel geoModel;
    private Marker placeMarker;
    
    private float currentLat;
    private float currentLng;
    
    private String centerGeoMap = "41.850033, -87.6500523";
//    private String centerGeoMap;

    private String insertedContact;
    private UploadedFile selectedFile;

    private List<String> insertedContacts;
    private List<String> uploadedImagesPath;
    private List<UploadedFile> uploadedFiles;
    
	private List<String> typesOfPlace;
	private List<String> categoriesOfPlace;

	
	@PostConstruct
    public void init() {
        geoModel = new DefaultMapModel();
//        centerGeoMap = currentLat + ", " + currentLng;
        
    }
	
	public PlaceMBean() {
		this.place = new Place();
		
		this.insertedContacts = new ArrayList<>();
		this.uploadedImagesPath = new ArrayList<>();
		this.uploadedFiles = new ArrayList<>();
		
		this.typesOfPlace = PlaceService.getTypesOfPlace();
		this.categoriesOfPlace = PlaceService.getCategoriesOfPlace();
	}
	
	public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();
             
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        }
    }
     
    public void onReverseGeocode(ReverseGeocodeEvent event) {
        List<String> addresses = event.getAddresses();
        LatLng coord = event.getLatlng();
         
        if (addresses != null && !addresses.isEmpty()) {
        	centerGeoMap = coord.getLat() + "," + coord.getLng();
            geoModel.addOverlay(new Marker(coord, addresses.get(0)));
        }
    }
    
    public void onPointSelect(PointSelectEvent event) {
		LatLng coord = event.getLatLng();
		centerGeoMap = coord.getLat() + "," + coord.getLng();
		
		if(placeMarker == null) {
			placeMarker = new Marker(coord);
			geoModel.addOverlay(placeMarker);
		} else {
			placeMarker.setLatlng(coord);
		}
		
		String resultAddress;
		try {
			resultAddress = getAddressByGpsCoordinates(coord.getLat(), coord.getLng());
		} catch (MalformedURLException e) {
			resultAddress = "";
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			resultAddress = "";
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			resultAddress = "";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.place.setAddress(resultAddress);
	}
    
    @SuppressWarnings("finally")
	private String getAddressByGpsCoordinates(double lat, double lng) throws MalformedURLException, IOException {
         
        URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&sensor=true");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String formattedAddress = "";
 
        try {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String result, line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            
            JSONObject rsp = new JSONObject(result);
            if (rsp.has("results")) {
                JSONArray matches = (JSONArray) rsp.get("results");
                JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
                formattedAddress = (String) data.get("formatted_address");
            }
 
            return "";
        } finally {
            urlConnection.disconnect();
            return formattedAddress;
        }
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
    	if(selectedFile != null) {
    		this.uploadedFiles.add(selectedFile);
    	}
    }
    
    public String register() {
		try {
			this.place.setContacts(insertedContacts);
			this.place.setImages(uploadedImagesPath);
			
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
	
	public UploadedFile getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(UploadedFile selectedFile) {
		this.selectedFile = selectedFile;
	}
	
    public MapModel getGeoModel() {
        return geoModel;
    }
 
    public String getCenterGeoMap() {
        return centerGeoMap;
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
	
	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
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
