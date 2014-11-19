package fr.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class SearchConfiguration {
	private JestClient client;

	public SearchConfiguration(){
		String connectionUrl = "https://site:9be823443528aa4d146cdfea023856f0@balin-eu-west-1.searchly.com";
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig
		       .Builder(connectionUrl)
		       .multiThreaded(true)
		       .build());
		 this.client = factory.getObject();
	}
	
	public JestClient getClient(){
		return this.client;
	}
}
