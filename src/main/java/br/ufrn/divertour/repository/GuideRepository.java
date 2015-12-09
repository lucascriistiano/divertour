package br.ufrn.divertour.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.User;

@Component
public class GuideRepository implements IGuideRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoGuideRepositoryCRUD mongoGuideRepositoryCRUD;
	
	@Override
	public void save(Guide guide) {
		mongoGuideRepositoryCRUD.save(guide);
	}

	@Override
	public void delete(String id) {
		mongoGuideRepositoryCRUD.delete(id);
	}

	@Override
	public Guide findById(String id) {
		return mongoGuideRepositoryCRUD.findById(id);
	}
	
	@Override
	public List<Guide> listAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	@Override
	public List<Guide> findByNameSimilarity(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(name, "i"));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	@Override
	public List<Guide> findByNumberOfPlaces(int numberOfPlaces) {
		Query query = new Query();
		query.addCriteria(Criteria.where("places").size(numberOfPlaces));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	@Override
	public List<Guide> findByPeriod(int period) {
		Query query = new Query();
		query.addCriteria(Criteria.where("period").is(period));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	@Override
	public List<Guide> findByCategory(String category) {
		Query query = new Query();
		query.addCriteria(Criteria.where("category").is(category));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	@Override
	public List<Guide> findByCityAndState(String cityName, String cityState) {
		Query query = new Query();
		query.addCriteria(Criteria.where("places.city.name").in(cityName).and("places.city.state").in(cityState));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}
	
	@Override
	public List<Guide> findByUser(User loggedUser) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user.username").is(loggedUser.getUsername()));
		//TODO order by date
		query.with(new Sort(Sort.Direction.DESC, "creationDate"));
		return mongoTemplate.find(query, Guide.class);
	}

	@Override
	public List<Integer> getPeriodsOfGuide() {
		@SuppressWarnings("unchecked")
		List<Integer> periods = mongoTemplate.getCollection("guide").distinct("period");
		return periods;
	}

	@Override
	public List<Integer> getNumberOfPlaces() {
		Query query = new Query();
		List<Guide> guides = mongoTemplate.find(query, Guide.class);
		Set<Integer> numPlacesSet = new HashSet<>();
		for (Guide guide : guides) {
			numPlacesSet.add(guide.getPlaces().size());
		}
		
		ArrayList<Integer> numPlaces = new ArrayList<>(numPlacesSet);
		Collections.sort(numPlaces);
		return numPlaces;
	}
	
}
