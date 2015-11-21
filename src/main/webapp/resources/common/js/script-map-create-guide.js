$(document).ready(getLocation);

function getLocation() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(success, error);
	} else {
		error(2); // Position unavailable on navigator
	}
}

function success(position) {
	var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

	var mapOptions = {
		center : latlng,
		zoom : 13,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		mapTypeControl: false
	};
	initAutocomplete(mapOptions);
}

function error(msg) {
	if (msg.code == 1) {
		// PERMISSION_DENIED
		console.log('PERMISSION_DENIED');
	} else if (msg.code == 2) {
		// POSITION_UNAVAILABLE
		console.log('POSITION_UNAVAILABLE');
	} else {
		console.log('TIMEOUT');
	} // TIMEOUT
	
	//Set brasil default position	
	var brasilLatLng = new google.maps.LatLng(-12.2072223,-54.3834182);
	
	var mapOptions = {
		center : brasilLatLng,
		zoom : 4,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		mapTypeControl: false
	};
	initAutocomplete(mapOptions);
}

//These variables must be global
var map;
var directionsDisplay;
var directionsService;

function initAutocomplete(mapOptions) {
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);
//	directionsDisplay.setPanel(document.getElementById("directionsPanel"));
}

var markers = {};

function addMarker() {
	var strPlace = $('#selected-place-json').val();
	var place = jQuery.parseJSON(strPlace);
	
	var placeLatLng = {lat: place['lat'], lng: place['lng']};
	var placeMarker = new google.maps.Marker({
	    position: placeLatLng,
	    map: map,
	    title: place['name']
	});
	
	var placeId = place['id'];
	markers[placeId] = placeMarker;
	
	// Has more than one point to make a route
	var dictSize = Object.keys(markers).length;
	if(dictSize >= 2) {
		var startPlaceLatLng, endPlaceLatLng;
		var waypointsLatLng = []; 
		var index = 0;
		for (var key in markers){
			var position = markers[key]['position'];
			var latLng = new google.maps.LatLng(position.lat(), position.lng());
			
			if(index == 0) {
		    	startPlaceLatLng = latLng;
		    } else if(index == (dictSize-1)) {
		    	endPlaceLatLng = latLng;
		    } else {
		    	waypointsLatLng.push(latLng);
		    }
			
			index++;
		}
		
		createRoute(startPlaceLatLng, waypointsLatLng, endPlaceLatLng);
	}
}


function createRoute(startPlaceLatLng, waypointsLatLng, endPlaceLatLng) {
	var waypts = [];
	for (var i = 0; i < waypointsLatLng.length; i++) {
		waypts.push({
			location: waypointsLatLng[i],
			stopover: true
		});
	}
	
	var request = {
		origin: startPlaceLatLng,
		destination: endPlaceLatLng,
		travelMode: google.maps.TravelMode.DRIVING,
		unitSystem: google.maps.UnitSystem.METRIC,
		waypoints: waypts
	}
	
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
	    	directionsDisplay.setDirections(response);
	    }
	});
}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
	for (var key in markers){
		var position = markers[key].setMap(map);
	}
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setMapOnAll(null);
}

// Shows any markers currently in the array.
function showMarkers() {
  setMapOnAll(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}