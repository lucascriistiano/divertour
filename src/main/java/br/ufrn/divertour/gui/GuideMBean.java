package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.exception.ValidationException;

@ManagedBean(name = "guideMBean")
@ViewScoped
public class GuideMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Guide guide;
	
	private Place selectedPlace;
	private List<Place> selectedPlaces;

	private PlaceService placeService = PlaceService.getInstance();
	private GuideService guideService = GuideService.getInstance();

	private MapModel geoModel;
    private List<Marker> placeMarkers;

    private String centerGeoMap = "41.850033, -87.6500523";
//    private String centerGeoMap;
	
	@PostConstruct
    public void init() {
        geoModel = new DefaultMapModel();
//        centerGeoMap = currentLat + ", " + currentLng;        
    }
	
	public GuideMBean() {
		this.guide = new Guide();
		this.selectedPlaces = new ArrayList<>();
		this.placeMarkers = new ArrayList<>();
	}
	
//	public void onGeocode(GeocodeEvent event) {
//        List<GeocodeResult> results = event.getResults();
//         
//        if (results != null && !results.isEmpty()) {
//            LatLng center = results.get(0).getLatLng();
//            centerGeoMap = center.getLat() + "," + center.getLng();
//             
//            for (int i = 0; i < results.size(); i++) {
//                GeocodeResult result = results.get(i);
//                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
//            }
//        }
//    }
     
//    public void onReverseGeocode(ReverseGeocodeEvent event) {
//        List<String> addresses = event.getAddresses();
//        LatLng coord = event.getLatlng();
//         
//        if (addresses != null && !addresses.isEmpty()) {
//        	centerGeoMap = coord.getLat() + "," + coord.getLng();
//            geoModel.addOverlay(new Marker(coord, addresses.get(0)));
//        }
//    }
    
//    public void onPointSelect(PointSelectEvent event) {
//		LatLng coord = event.getLatLng();
//		centerGeoMap = coord.getLat() + "," + coord.getLng();
//		
//		if(placeMarker == null) {
//			placeMarker = new Marker(coord);
//			geoModel.addOverlay(placeMarker);
//		} else {
//			placeMarker.setLatlng(coord);
//		}
//		
//		String resultAddress;
//		try {
//			resultAddress = getAddressByGpsCoordinates(coord.getLat(), coord.getLng());
//		} catch (MalformedURLException e) {
//			resultAddress = "";
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			resultAddress = "";
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch(Exception e) {
//			resultAddress = "";
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		this.place.setAddress(resultAddress);
//	}
	
    public void addPlace() {
    	if(selectedPlace != null) {
    		this.selectedPlaces.add(selectedPlace);
    		
    		// Add place marker to map
    		LatLng selectedPlaceCoords = new LatLng(selectedPlace.getLat(), selectedPlace.getLng());
    		Marker selectedPlaceMarker = new Marker(selectedPlaceCoords);
    		placeMarkers.add(selectedPlaceMarker);
    		geoModel.addOverlay(selectedPlaceMarker);
    		
    		// Center map on added marker    		
    		centerGeoMap = selectedPlace.getLat() + ", " + selectedPlace.getLng();
    		
			// Deselect place	
    		this.selectedPlace = null;
    	}
    }

	public String register() {
		try {
			guide.setPlaces(selectedPlaces);
			this.guideService.register(guide);
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
		System.out.println("Edit guide with id = " + id);
		return "";
	}
	
	public String remove(String id) {
		this.placeService.remove(id);
		return "";
	}
	
	public List<Place> getPlaces() {
		return placeService.listAll();
	}
	
	public List<Place> getSelectedPlaces() {
		return selectedPlaces;
	}

	public List<Marker> getPlaceMarkers() {
		return placeMarkers;
	}

	public Place getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(Place selectedPlace) {
		this.selectedPlace = selectedPlace;
	}

    public MapModel getGeoModel() {
        return geoModel;
    }
 
    public String getCenterGeoMap() {
        return centerGeoMap;
    }

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}
    
	public List<Guide> getGuides() {
		return guideService.listAll();
	}
	
	public List<String> getCategoriesOfGuide() {
		return GuideService.getCategoriesOfGuide();
	}
	
}
