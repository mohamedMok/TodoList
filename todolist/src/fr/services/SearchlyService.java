package fr.services;

import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import fr.config.SearchConfiguration;
import fr.model.Task;

public class SearchlyService {

	private SearchConfiguration searchConfig = new SearchConfiguration();

	public SearchlyService() {

	}

	public void addDocumentToIndex(Task t) throws Exception {
		Index index = new Index.Builder(t).index("tasks").type("task").build();
		this.searchConfig.getClient().execute(index);
	}

	public void updateDocument(Task t) throws Exception {
		addDocumentToIndex(t);
	}

	public void removeFromIndex(String taskId) {
		try {
			this.searchConfig.getClient().execute(
					new Delete.Builder(taskId).index("tasks").type("task")
							.build());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Task> search(String query) throws Exception {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.multiMatchQuery(query, "title^3", "body"));
		 
		Search search = (Search) new Search.Builder(searchSourceBuilder.toString())
		                                .addIndex("tasks")
		                                .addType("task")
		                                .build();
		 
		JestResult result = this.searchConfig.getClient().execute(search);
		List<Task> tasks = result.getSourceAsObjectList(Task.class);
		return tasks;
	}

}
