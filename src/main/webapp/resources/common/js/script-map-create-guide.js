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
	
	map.addListener('idle', function() {
		$("#find-places-on-area-btn").click();
	});
	
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);
//	directionsDisplay.setPanel(document.getElementById("directionsPanel"));
}

var markers = {};
var onAreaPlaces = {};
var routePlaces = {};

function addSearchedPlace() {
	var strPlace = $('#searched-place-json').val();
	var place = jQuery.parseJSON(strPlace);
	
	addPlaceToRoute(place);
}

function addPlaceMarkerToMap(place, icon) {
	var placeMarker = new google.maps.Marker({
		position: {lat: place['lat'], lng: place['lng']},
		map: map,
		title: place['name'],
		icon: icon
	});
	
	placeMarker.addListener('click', function() {
//		if(!(placeId in routePlaces)) {
			addPlaceToRoute(place);
//		} else {
//			removePlaceFromRoute(place);
//		}
	});
	
	var placeId = place['id'];
	markers[placeId] = placeMarker;
}

function addPlaceToRoute(place) {
	//Remove temporary created route place marker
	var placeId = place['id'];
	if(placeId in markers) {
		var placeMarker = markers[placeId];
		placeMarker.setMap(null);
		delete markers[placeId];
	}

	routePlaces[placeId] = place;
	updateRoute();
}

//function removePlaceFromRoute(place) {
//	//Remove temporary created route place marker
//	delete routePlaces[placeId];
//	
//	var placeId = place['id'];
//	if(placeId in onAreaPlaces) {
//		addPlaceMarkerToMap(place, 'https://maps.google.com/mapfiles/kml/shapes/info-i_maps.png');
//	}
//
//	updateRoute();
//}

function updateRoute() {
	var dictSize = Object.keys(routePlaces).length;
	if(dictSize >= 2) {
		// Has more than one point to make a route
		var startPlaceLatLng, endPlaceLatLng;
		var waypointsLatLng = []; 
		var index = 0;
		for (var key in routePlaces){
			var place = routePlaces[key];
			var latLng = new google.maps.LatLng(place['lat'], place['lng']);
			
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
	} else if(dictSize == 1) {
		// Has only the first point of route
		for (var key in routePlaces){
			var place = routePlaces[key];
			addPlaceMarkerToMap(place, 'https://maps.google.com/mapfiles/kml/shapes/schools_maps.png');
			break;
		}
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
function setMapOnAll(object, map) {
	for (var key in object){
		object[key].setMap(map);
	}
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers(object) {
  setMapOnAll(object, null);
}

// Shows any markers currently in the array.
function showMarkers(object) {
  setMapOnAll(object, map);
}

function togglePlaceOnRoute(id) {
	
}

function updateAreaPlaces(xhr, status, args) {
	//Clear previous markers
//	clearMarkers(onAreaPlaces);
//	onAreaPlaces = {};
	
	clearMarkers(markers);
	markers = {};
	
	var places = JSON.parse(args.places);
	for (var i=0; i < places.length; i++) {
		var place = places[i];
		var placeId = place['id'];
		onAreaPlaces[placeId] = place;
		if(!(placeId in routePlaces)) {
			console.log(place);
			addPlaceMarkerToMap(place, 'https://maps.google.com/mapfiles/kml/shapes/info-i_maps.png');
		}
	}
	
	if(Object.keys(routePlaces).length == 1) {
		for (var key in routePlaces){
			var place = routePlaces[key];
			addPlaceMarkerToMap(place, 'https://maps.google.com/mapfiles/kml/shapes/schools_maps.png');
			break;
		}
	}
}