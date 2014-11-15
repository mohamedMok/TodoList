package fr.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import fr.connection.DBConnection;
import fr.model.Task;

public class TaskDAO {
	DBCollection collection;
	
	public TaskDAO(){
		this.collection = DBConnection.getInstance().getDB().getCollection("TASKS");
	}
	
	public List<Task> getAllTasks(){
		List<Task> listTasks = new ArrayList<Task>();
		DBCursor cursor = collection.find();
		while(cursor.hasNext()){
			DBObject dboject = cursor.next();
			Task t = new Task();
			t.setTitle(dboject.get("title").toString());
			t.setBody(dboject.get("body").toString());
			t.setDone(Boolean.getBoolean(dboject.get("done").toString()));
			t.setId(dboject.get("_id").toString());
			listTasks.add(t);
		}
		return listTasks;
	}
	
}
