function initializeMap() {
	getLocation();
}

$(document).ready(initializeMap);
//google.maps.event.addDomListener(window, 'load', initializeMap);

function getLocation() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(success, error);
	} else {
		// Position unavailable on navigator		
		error(2);
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

//var searchBox;
//
//// Searchbar
function initAutocomplete(mapOptions) {
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	directionsService = new google.maps.DirectionsService();
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);
//	directionsDisplay.setPanel(document.getElementById("directionsPanel"));

//	// Create the search box and link it to the UI element.
//	var input = document.getElementById('pac-input');
//	map.controls[google.maps.ControlPosition.TOP_CENTER].push(input);
//	searchBox = new google.maps.places.SearchBox(input);
////	input.style.display = 'block';
//
//	// Bias the SearchBox results towards current map's viewport.
//	map.addListener('bounds_changed', function() {
//		searchBox.setBounds(map.getBounds());
//	});
//
//	var markers = [];
//	// [START region_getplaces]
//	// Listen for the event fired when the user selects a prediction and
//	// retrieve
//	// more details for that place.
//	searchBox.addListener('places_changed', function() {
//		var places = searchBox.getPlaces();
//
//		if (places.length == 0) {
//			return;
//		}
//
//		// Clear out the old markers.
//		markers.forEach(function(marker) {
//			marker.setMap(null);
//		});
//		markers = [];
//
//		// For each place, get the icon, name and location.
//		var bounds = new google.maps.LatLngBounds();
//		places.forEach(function(place) {
//			var icon = {
//				url : place.icon,
//				size : new google.maps.Size(71, 71),
//				origin : new google.maps.Point(0, 0),
//				anchor : new google.maps.Point(17, 34),
//				scaledSize : new google.maps.Size(25, 25)
//			};
//
//			// Create a marker for each place.
//			markers.push(new google.maps.Marker({
//				map : map,
//				icon : icon,
//				title : place.name,
//				position : place.geometry.location
//			}));
//
//			if (place.geometry.viewport) {
//				// Only geocodes have viewport.
//				bounds.union(place.geometry.viewport);
//			} else {
//				bounds.extend(place.geometry.location);
//			}
//		});
//		map.fitBounds(bounds);
//	});
//	// [END region_getplaces]
}

$(function() {
	$('#pac-input').show();
})

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