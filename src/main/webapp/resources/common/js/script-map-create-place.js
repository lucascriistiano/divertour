$(document).ready(getLocation);

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
	map = new google.maps.Map(document.getElementById('place-form:map-canvas'), mapOptions);
	geocoder = new google.maps.Geocoder();
	
	google.maps.event.addListener(map, 'click', function(event) {
		selectPlace(event.latLng);
	});
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
function addChooseableMarker(location) {
	var marker = new google.maps.Marker({
		map: map,
		position: location
	});

	marker.addListener('click', function() {
		var position = this.getPosition();
		selectPlace(position);
	});
	
	markers.push(marker);
}

function addMarker(location) {
	var marker = new google.maps.Marker({
		map: map,
		position: location
	});
	markers.push(marker);
}

function selectPlace(location) {
	deleteMarkers();
	addMarker(location);
	setPlaceLocation(location);
	map.setCenter(location);
	geocodeAddress(location);
}

function geocodeAddress(location) {
	geocoder.geocode({'location': location}, function(results, status) {
		if (status === google.maps.GeocoderStatus.OK) {
			if (results[1]) {
				var address = results[1].formatted_address;
				setPlaceAddress(address);
			} else {
				console.log('No address results found');
			}
		} else {
			console.log('Geocoder failed due to: ' + status);
		}
	});
}

function setPlaceLocation(location) {
	$(document.getElementById('place-form:selected-lat')).val(location.lat());
	$(document.getElementById('place-form:selected-lng')).val(location.lng());
}

function setPlaceAddress(address) {
	$(document.getElementById('place-form:address')).val(address);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}

function geocode() {
	var address = $(document.getElementById('place-form:address')).val();
	
	var city = '';
	var cityCombo = document.getElementById('place-form:city');
    if (cityCombo.selectedIndex != -1) {
    	city = cityCombo.options[cityCombo.selectedIndex].text;
    }
	
	var completeAddress = address + ', ' + city + ', Brasil';
	console.log(completeAddress);
	
	deleteMarkers();
	
	geocoder.geocode({'address': completeAddress}, function(results, status) {
		if (status === google.maps.GeocoderStatus.OK) {
			console.log(results);
			
			if(results.length == 1) {
				var location = results[0].geometry.location;
				selectPlace(location);
			} else if(results.length > 1) {
				//Add found locations to map
				for(var i = 0; i < results.length; i++) {
					var location = results[i].geometry.location;
					addChooseableMarker(location);
				}
			
				//Adjust map to show all markers
				var bounds = new google.maps.LatLngBounds();
				for(var i = 0; i < markers.length; i++) {
					bounds.extend(markers[i].getPosition());
				}
				map.fitBounds(bounds);
				
				alert('Foi encontrado mais de um resultado com os parâmetros escolhidos. Clique sobre o marcador da localização correta ou sobre um ponto do mapa para adicionar um novo marcador, caso nenhum corresponda ao ponto a ser cadastrado.');
			}
			
		} else if (status == "ZERO_RESULTS") {
			alert('Não foram encontradas coordenadas para os parâmetros passados. Verifique o endereço inserido ou clique em uma posição aproximada sobre o mapa para adicionar um marcador de posição.');
		} else {
			console.log('Geocode falhou pela seguinte razão: ' + status);
		}
	});
}