package br.ufrn.divertour.repository;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.exception.ValidationException;

public class TestMongo {
	
	public static void main(String[] args) throws ValidationException {
		City city = new City("Natal", "RN", -5.8017776f, -35.2923621f);
		CityService.getInstance().register(city);
		System.out.println("Generated city ID: " + city.getId());
		
		City city2 = new City("Mossoró", "RN", -5.195062f, -37.4116811f);
		CityService.getInstance().register(city2);
		System.out.println("Generated city ID: " + city2.getId());
		
		City city3 = new City("São Paulo", "SP", -23.6815314f, -46.8754984f);
		CityService.getInstance().register(city3);
		System.out.println("Generated city ID: " + city3.getId());
		
//		User user = new User();
//		user.setAdmin(false);
//		user.setName("João da Silva");
//		user.setEmail("joao@dasilva.com.br");
//		user.setUsername("joaodasilva");
//		user.setPassword("123asd123");
//		user.setCity(city);
//		user.setInterests(Arrays.asList("Natural", "Ecológico"));
//		user.setNotifications(Arrays.asList());
//		UserService.getInstance().register(user);
//		System.out.println("Generated user ID: " + user.getId());
//		
//		User user2 = new User();
//		user2.setAdmin(false);
//		user2.setName("Maria Joana");
//		user2.setEmail("maria@gmail.com");
//		user2.setUsername("mariajoana");
//		user2.setPassword("as721ha9");
//		user2.setCity(city);
//		user2.setInterests(Arrays.asList("Natural", "Ecológico"));
//		user2.setNotifications(Arrays.asList());
//		UserService.getInstance().register(user2);
//		System.out.println("Generated user ID: " + user2.getId());
		
//		Place place = new Place();
//		place.setName("Parque das Dunas");
//		place.setDescription("Um parque no meio da cidade");
//		place.setCity(city);
//		place.setLat(-5.811285f);
//		place.setLng(-35.193876f);
//		place.setRating(4);
//		place.setCategories(Arrays.asList("Natural", "Ecológico"));
//		place.setImages(Arrays.asList("pddunas1.jpg", "pddunas2.jpg"));
//		place.setContacts(Arrays.asList("(84) 1267-2138", "(84) 6347-3865"));
//		place.setWebsite("http://www.parquedasdunas.com.br");
//		place.setComments(Arrays.asList(new Comment("Lugar legal", "Fui no parque com minha família e achamos muito legal!", new Date(), 4, user.getId())));
//		PlaceService.getInstance().register(place);
//		System.out.println("Generated place ID: " + place.getId());
		
//		Place place2 = new Place();
//		place2.setName("Fortaleza dos Reis Magos");
//		place2.setDescription("Uma fortaleza histórica");
//		place2.setCity(city);
//		place2.setLat(-5.756522f);
//		place2.setLng(-35.194983f);
//		place2.setRating(4);
//		place2.setCategories(Arrays.asList("Histórico"));
//		place2.setImages(Arrays.asList("forte1.jpg", "forte2.jpg"));
//		place2.setContacts(Arrays.asList("(84) 3427-2458", "(84) 3247-3765"));
//		place2.setWebsite("http://www.fortedosreismagos.com.br");
//		place2.setComments(Arrays.asList());
//		PlaceService.getInstance().register(place2);
//		System.out.println("Generated place ID: " + place2.getId());
		
//		Route route = new Route();
//		route.setName("Natal Natural");
//		route.setCategory("Ecológico");
//		route.setCreationDate(new Date());
//		route.setPeriod(1);
//		route.setRating(4);
//		route.setComments(Arrays.asList(new Comment("Rota muito ecológica", "Muito verde por todo lado", new Date(), 5, user2.getId())));
//
//		//remove comments of object to not insert them on route
//		place.setComments(null);
//		route.setPlaces(Arrays.asList(place));
//		RouteService.getInstance().register(route);
//		System.out.println("Generated route ID: " + route.getId());
		
//		List<City> cities = CityService.getInstance().listAll();
//		for (City city : cities) {
//			System.out.println("Name: " + city.getName());
//		}
	}
}
