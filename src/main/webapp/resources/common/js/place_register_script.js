/**
 * 
 */

function geocode() {
	PF('geoMap').geocode(document.getElementById('address').value);
}

function reverseGeocode() {
	var lat = document.getElementById('lat').value, lng = document.getElementById('lng').value;

	PF('geoMap').reverseGeocode(lat, lng);
}

function getLocationConstant()
{
    if(navigator.geolocation)
    {
        navigator.geolocation.getCurrentPosition(onGeoSuccess,onGeoError);  
    } else {
        alert("Your browser or device doesn't support Geolocation");
    }
}

// If we have a successful location update
function onGeoSuccess(event)
{
    document.getElementById("currentLat").value =  event.coords.latitude;
    document.getElementById("currentLng").value = event.coords.longitude;
}

// If something has gone wrong with the geolocation request
function onGeoError(event)
{
    alert("Error code " + event.code + ". " + event.message);
}