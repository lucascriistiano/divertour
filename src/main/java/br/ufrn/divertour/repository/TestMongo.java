package br.ufrn.divertour.repository;

import java.util.Arrays;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.Comment;
import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.UserService;
import br.ufrn.divertour.service.exception.ValidationException;

public class TestMongo {
	
	public static void main(String[] args) throws ValidationException {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CityService cityService = (CityService) context.getBean(CityService.class);
		UserService userService = (UserService) context.getBean(UserService.class);
		PlaceService placeService = (PlaceService) context.getBean(PlaceService.class);
		GuideService guideService = (GuideService) context.getBean(GuideService.class);
		
		City city = cityService.findByNameAndState("Natal", "RN");
		City city2 = cityService.findByNameAndState("Mossoró", "RN");
		City city3 = cityService.findByNameAndState("São Paulo", "SP");
		
		User userAdmin = new User();
		userAdmin.setAdmin(false);
		userAdmin.setName("Administrador");
		userAdmin.setEmail("admin@divertour.com.br");
		userAdmin.setUsername("admin");
		userAdmin.setPassword("admin");
		userAdmin.setCity(city);
		userAdmin.setInterests(Arrays.asList());
		userAdmin.setNotifications(Arrays.asList());
		userAdmin.setProfileImage("01.jpg");
		userAdmin.setAdmin(true);
		userService.register(userAdmin);
		System.out.println("Generated user ID: " + userAdmin.getId());
		
		User user = new User();
		user.setAdmin(false);
		user.setName("João da Silva");
		user.setEmail("joao@dasilva.com.br");
		user.setUsername("joaodasilva");
		user.setPassword("123asd123");
		user.setCity(city);
		user.setInterests(Arrays.asList("Natural", "Ecológico"));
		user.setNotifications(Arrays.asList());
		user.setProfileImage("01.jpg");
		userService.register(user);
		System.out.println("Generated user ID: " + user.getId());
		
		User user2 = new User();
		user2.setAdmin(false);
		user2.setName("Maria Joana");
		user2.setEmail("maria@gmail.com");
		user2.setUsername("mariajoana");
		user2.setPassword("as721ha9");
		user2.setCity(city);
		user2.setInterests(Arrays.asList("Natural", "Ecológico"));
		user2.setNotifications(Arrays.asList());
		user2.setProfileImage("02.jpg");
		userService.register(user2);
		System.out.println("Generated user ID: " + user2.getId());
		
		Place place = new Place();
		place.setName("Parque das Dunas");
		place.setDescription("Um parque no meio da cidade");
		place.setCity(city);
		place.setLat(-5.811285f);
		place.setLng(-35.193876f);
		place.setRating(4);
		place.setType("Ponto Turístico");
		place.setCategories(Arrays.asList("Natural", "Ecológico"));
		place.setImages(Arrays.asList("pddunas1.jpg", "pddunas2.jpg"));
		place.setContacts(Arrays.asList("(84) 1267-2138", "(84) 6347-3865"));
		place.setWebsite("http://www.parquedasdunas.com.br");
		place.setComments(Arrays.asList(new Comment("Fui no parque com minha família e achamos muito legal!", new Date(), 4, user.getId())));
		placeService.register(place);
		System.out.println("Generated place ID: " + place.getId());
		
		Place place2 = new Place();
		place2.setName("Fortaleza dos Reis Magos");
		place2.setDescription("Uma fortaleza histórica");
		place2.setCity(city);
		place2.setLat(-5.756522f);
		place2.setLng(-35.194983f);
		place2.setRating(4);
		place2.setType("Ponto Turístico");
		place2.setCategories(Arrays.asList("Histórico"));
		place2.setImages(Arrays.asList("forte1.jpg", "forte2.jpg"));
		place2.setContacts(Arrays.asList("(84) 3427-2458", "(84) 3247-3765"));
		place2.setWebsite("http://www.fortedosreismagos.com.br");
		place2.setComments(Arrays.asList());
		placeService.register(place2);
		System.out.println("Generated place ID: " + place2.getId());
		
		Place place3 = new Place();
		place3.setName("Midway Mall");
		place3.setDescription("Um shopping legal");
		place3.setCity(city);
		place3.setLat(-5.811773f);
		place3.setLng(-35.206319f);
		place3.setRating(5);
		place3.setType("Comércio");
		place3.setCategories(Arrays.asList("Comercial"));
		place3.setImages(Arrays.asList("midway1.jpg"));
		place3.setContacts(Arrays.asList("(84) 1232-2312", "(84) 34534-5675"));
		place3.setWebsite("http://www.midwaymall.com.br");
		place3.setComments(Arrays.asList());
		placeService.register(place3);
		System.out.println("Generated place ID: " + place3.getId());
		
		Guide guide = new Guide();
		guide.setName("Natal Natural");
		guide.setCategory("Ecológico");
		guide.setCreationDate(new Date());
		guide.setPeriod(1);
		guide.setRating(4);
		guide.setComments(Arrays.asList(new Comment("Muito verde por todo lado", new Date(), 5, user2.getId())));

		//remove comments of object to not insert them on guide
		place.setComments(null);
		guide.setPlaces(Arrays.asList(place, place2));
		guideService.register(guide);
		System.out.println("Generated guide ID: " + guide.getId());
	}
}
