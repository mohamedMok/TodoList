package fr.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
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
	
	public boolean deleteTask(String id){
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("_id", new ObjectId(id));
		DBObject item = collection.findOne(deleteQuery);
		collection.remove(item);
		return true;
	}
	
	public Task getTaskById(String id){
		BasicDBObject query = new BasicDBObject();
		query.put("_id",new ObjectId(id));
		DBObject item = collection.findOne(query);
		Task t = new Task();
		t.setTitle(item.get("title").toString());
		t.setBody(item.get("body").toString());
		t.setDone(Boolean.getBoolean(item.get("done").toString()));
		t.setId(item.get("_id").toString());
		return t;
	}
}
