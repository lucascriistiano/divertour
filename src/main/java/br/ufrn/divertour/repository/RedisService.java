package br.ufrn.divertour.repository;

import java.net.URL;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.model.User;

//@Component
public class RedisService {

//	public static final String USER_HASH_PREFIX = "session:user";
//	
//	private RedisTemplate<String, String> template; // inject the template as ListOperations
//
//	private HashOperations<String, Object, Object> hashOps;
//	
//	@SuppressWarnings("unchecked")
//	@Autowired
//	public RedisService(RedisTemplate<String, String> template) {
//		this.template = template;
//		this.hashOps = template.opsForHash();
//	}
//
//	private String getUserHashKey(User user) {
//		return USER_HASH_PREFIX + ":" + user.getId(); 
//	}
//	
//	public void beginUserSession(User user) {
//		String userHashKey = getUserHashKey(user);
//		this.hashOps.put(userHashKey, "name", user.getName());
//		this.hashOps.put(userHashKey, "username", user.getUsername());
//		this.set
//	}
//	
//	public void refreshUserSession(User user) {
//		listOps.leftPush(userId, url);
//	}
//	
//	public void addLink(String userId, String url) {
//		listOps.leftPush(userId, url);
//	}
	
//	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
////		context.getBean("")
//		new Redis().addLink("lucas", "aushdaisduhasiduhasidu");
//	}
}
