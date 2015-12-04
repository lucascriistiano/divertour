package br.ufrn.divertour.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.model.City;

@Component
public class CityRepository implements ICityRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoCityRepositoryCRUD mongoCityRepositoryCRUD;

	@Override
	public void save(City city) {
		mongoCityRepositoryCRUD.save(city);
	}

	@Override
	public void delete(String id) {
		mongoCityRepositoryCRUD.delete(id);
	}
	
	@Override
	public List<City> listAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "name"));
		return mongoTemplate.find(query, City.class);
	}

	@Override
	public City findByNameAndState(String name, String state) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name).and("state").is(state));
		query.with(new Sort(Sort.Direction.ASC, "name"));
		return mongoTemplate.findOne(query, City.class);
	}
	
}
