package fr.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class TodoList extends ResourceConfig{
	
	public TodoList(){
		packages("fr.resources");
	}
	
	
	

}
