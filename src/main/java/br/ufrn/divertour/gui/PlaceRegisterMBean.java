package br.ufrn.divertour.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.PlaceService;

@ManagedBean(name = "placeRegisterMBean")
public class PlaceRegisterMBean {

	private Place place;
	private PlaceService placeService = PlaceService.getInstance();
	
	private MapModel geoModel;
    private Marker placeMarker;
    private String centerGeoMap = "41.850033, -87.6500523";

    private List<UploadedFile> uploadedFiles;
    private UploadedFile selectedFile;
    
	private List<String> typesOfPlace;
	private List<String> categoriesOfPlace;
	
	@PostConstruct
    public void init() {
        geoModel = new DefaultMapModel();
    }
	
	public PlaceRegisterMBean() {
		this.place = new Place();
		
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
		
		System.out.println("Lat: " + coord.getLat());
		System.out.println("Lng: " + coord.getLng());
	}
	
    public void addImage() {
    	if(selectedFile != null) {
    		this.uploadedFiles.add(selectedFile);
    	}
    }
    
	public String register() {
		//TODO Implement try/catch business exception
		this.placeService.register(this.place);
		
		//TODO Change return
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

	public List<String> getTypesOfPlace() {
		return typesOfPlace;
	}

	public List<String> getCategoriesOfPlace() {
		return categoriesOfPlace;
	}
	
	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}
	
}
