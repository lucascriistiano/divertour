package br.ufrn.divertour.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.model.LatLng;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.User;

@Component
public class PlaceRepository implements IPlaceRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoPlaceRepositoryCRUD mongoPlaceRepositoryCRUD;
	
	@Override
	public void save(Place place) {
		mongoPlaceRepositoryCRUD.save(place);
	}

	@Override
	public void delete(String id) {
		mongoPlaceRepositoryCRUD.delete(id);
	}

	@Override
	public Place findById(String id) {
		return mongoPlaceRepositoryCRUD.findById(id);
	}

	@Override
	public Place findByName(String name) {
		return mongoPlaceRepositoryCRUD.findByName(name);
	}
	
	@Override
	public List<Place> listAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Place.class);
	}

	@Override
	public List<Place> findByNameSimilarity(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(name, "i"));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Place.class);
	}

	@Override
	public List<Place> findByCityAndState(String city, String state) {
		Query query = new Query();
		query.addCriteria(Criteria.where("city.name").is(city).and("city.state").is(state));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Place.class);
	}

	@Override
	public List<Place> findByType(String type) {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Place.class);
	}

	@Override
	public List<Place> findByCategory(String category) {
		Query query = new Query();
		query.addCriteria(Criteria.where("categories").in(category));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Place.class);
	}
	
	@Override
	public List<Place> findByUser(User loggedUser) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user.username").is(loggedUser.getUsername()));
		//TODO order by date
		query.with(new Sort(Sort.Direction.DESC, "creationDate"));
		return mongoTemplate.find(query, Place.class);
	}

	@Override
	public List<Place> findOnArea(LatLng upperBound, LatLng lowerBound) {
		// TODO finish query
		Query query = new Query();
		// query.addCriteria(Criteria.where("lat").gte(o))
		return mongoTemplate.find(query, Place.class);
	}

}
