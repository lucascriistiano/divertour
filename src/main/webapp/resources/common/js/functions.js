   	$(document).ready(function() {
		$(".divsRow > .equalizer").each(function() { 
		    var height = 0;
		    $(this).find(".watch").each(function() {
		      if($(this).height() > height){
		    	height = $(this).height();
		      }
		    });
		    $(this).find(".watch").height(height);
	  	});
	});       
   	
   	$(function(){
   		$('#description').css('height', '100%').css('height', '-=35px');   		
   	});