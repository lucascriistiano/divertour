package br.ufrn.divertour.service;

import java.net.URL;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

//@Component
public class Redis {
//
//	// inject the actual template
//	@Autowired
//	private RedisTemplate<String, String> template; // inject the template as
//													// ListOperations
//
//	@Resource(name = "redisTemplate")
//	private ListOperations<String, String> listOps;
//
//	public void addLink(String userId, String url) {
//		listOps.leftPush(userId, url);
//	}
//	
//	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
////		context.getBean("")
//		new Redis().addLink("lucas", "aushdaisduhasiduhasidu");
//	}
}
