function initializeMap() {
	getLocation();
}

$(document).ready(initializeMap);

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
var geocoder;
var markers = [];

// Searchbar
function initAutocomplete(mapOptions) {
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	geocoder = new google.maps.Geocoder();
}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
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

//Deletes all markers in the array by removing references to them.
function createMarker(location) {
	var marker = new google.maps.Marker({
		map: map,
		position: location
	});
	markers.push(marker);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}

function geocode() {
	var address = $('#address').val();
	var city = $('#city option:selected').text();
	
	var completeAddress = address + ', ' + city;
	console.log(completeAddress);
	
	deleteMarkers();
	
	geocoder.geocode({'address': address}, function(results, status) {
		if (status === google.maps.GeocoderStatus.OK) {
			var location = results[0].geometry.location;
			map.setCenter(location);
			createMarker(location);
		} else if (status == "ZERO_RESULTS") {
			alert('Não foram encontradas coordenadas para os parâmetros passados. Verifique o endereço inserido ou clique em uma posição aproximada sobre o mapa para adicionar um marcador de posição.');
		} else {
			console.log('Geocode was not successful for the following reason: ' + status);
		}
	});
}