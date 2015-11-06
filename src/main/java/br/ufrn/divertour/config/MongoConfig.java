package br.ufrn.divertour.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import br.ufrn.divertour.repository.RepositoryPackage;
import br.ufrn.divertour.template.TemplatePackage;

@Configuration
@EnableMongoRepositories(basePackageClasses=RepositoryPackage.class)
@ComponentScan(basePackageClasses=TemplatePackage.class)
public class MongoConfig extends AbstractMongoConfiguration {

	// MongoDB config
	@Override
	protected String getDatabaseName() {
		return "divertour";
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClient client = new MongoClient("localhost", 27017);
		client.setWriteConcern(WriteConcern.SAFE);
		return client;
	}

	@Override
	protected String getMappingBasePackage() {
		return "br.ufrn.divertour.model";
	}

	// MongoTemplate
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}

}
