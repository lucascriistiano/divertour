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
		mapTypeId : google.maps.MapTypeId.ROADMAP
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
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	initAutocomplete(mapOptions);
}

//These variables must be global
var map;
var searchBox;

// Searchbar
function initAutocomplete(mapOptions) {
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

	// Create the search box and link it to the UI element.
	var input = document.getElementById('pac-input');
	map.controls[google.maps.ControlPosition.TOP_CENTER].push(input);
	searchBox = new google.maps.places.SearchBox(input);

	// Bias the SearchBox results towards current map's viewport.
	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});

	var markers = [];
	// [START region_getplaces]
	// Listen for the event fired when the user selects a prediction and
	// retrieve
	// more details for that place.
	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();

		if (places.length == 0) {
			return;
		}

		// Clear out the old markers.
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
		markers = [];

		// For each place, get the icon, name and location.
		var bounds = new google.maps.LatLngBounds();
		places.forEach(function(place) {
			var icon = {
				url : place.icon,
				size : new google.maps.Size(71, 71),
				origin : new google.maps.Point(0, 0),
				anchor : new google.maps.Point(17, 34),
				scaledSize : new google.maps.Size(25, 25)
			};

			// Create a marker for each place.
			markers.push(new google.maps.Marker({
				map : map,
				icon : icon,
				title : place.name,
				position : place.geometry.location
			}));

			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});
		map.fitBounds(bounds);
	});
	// [END region_getplaces]
}