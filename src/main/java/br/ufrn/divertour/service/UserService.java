package br.ufrn.divertour.service;

import br.ufrn.divertour.model.User;

public class UserService {
	
	public void register(User user) {
		// TODO Implement validation
		
		System.out.println(user.getName());
		System.out.println(user.getLogin());
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		System.out.println(user.getCity());
	}

}
