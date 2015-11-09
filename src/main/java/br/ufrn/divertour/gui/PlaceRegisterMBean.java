package br.ufrn.divertour.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

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

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.PlaceService;

@ManagedBean(name = "placeRegisterMBean")
public class PlaceRegisterMBean {

	private Place place;
	private PlaceService placeService = PlaceService.getInstance();
	
	private MapModel geoModel;
    private Marker placeMarker;
    
    private float currentLat;
    private float currentLng;
    
    private String centerGeoMap = "41.850033, -87.6500523";
//    private String centerGeoMap;

    private List<UploadedFile> uploadedFiles;
    private UploadedFile selectedFile;
    
	private List<String> typesOfPlace;
	private List<String> categoriesOfPlace;
	
	@PostConstruct
    public void init() {
        geoModel = new DefaultMapModel();
//        centerGeoMap = currentLat + ", " + currentLng;
        
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
