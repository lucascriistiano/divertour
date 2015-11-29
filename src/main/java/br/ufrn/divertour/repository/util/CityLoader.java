package br.ufrn.divertour.repository.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.exception.ValidationException;

public class CityLoader {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CityService cityService = (CityService) context.getBean(CityService.class);
		
		JsonObject object = convertFileToJSON("src/main/resources/estados-cidades.json");
		JsonArray arrayEstados = object.get("estados").getAsJsonArray();
		
		for(final JsonElement estado : arrayEstados) {
			JsonObject estadoObject = estado.getAsJsonObject();
			String siglaEstado = estadoObject.get("sigla").getAsString();
//			String nomeEstado = estadoObject.get("nome").getAsString();
			JsonArray cidadesArray = estadoObject.get("cidades").getAsJsonArray();
			
			for (JsonElement cidadeElement : cidadesArray) {
				String nomeCidade = cidadeElement.getAsString();
				System.out.println(nomeCidade + "/" + siglaEstado);
				
				City city = new City(nomeCidade, siglaEstado);
				try {
					cityService.register(city);
					System.out.println("Cidade adicionada");
				} catch (ValidationException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static JsonObject convertFileToJSON(String fileName) {
		// Read from File to String
		JsonObject jsonObject = new JsonObject();

		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fileName));
			jsonObject = jsonElement.getAsJsonObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

}
