package fr.dao;

import fr.connection.DBConnection;
import fr.model.Task;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class TaskDAO {
	DBCollection collection;

	public TaskDAO() {
		this.collection = DBConnection.getInstance().getDB()
				.getCollection("TASKS");
	}

	public List<Task> getAllTasks() {
		List<Task> listTasks = new ArrayList<Task>();
		DBCursor cursor = this.collection.find();
		while (cursor.hasNext()) {
			DBObject dboject = cursor.next();
			Task t = new Task();
			t.setTitle(dboject.get("title").toString());
			t.setBody(dboject.get("body").toString());
			t.setDone(Boolean.parseBoolean(dboject.get("done").toString()));
			t.setId(dboject.get("_id").toString());
			listTasks.add(t);
		}
		return listTasks;
	}

	public int deleteTask(String id) {
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("_id", new ObjectId(id));
		WriteResult res = this.collection.remove(deleteQuery);
		return res.getN();
	}

	public String create(Task t) {
		BasicDBObject newTask = new BasicDBObject();
		newTask.put("title", t.getTitle());
		newTask.put("body", t.getBody());
		newTask.put("done", t.isDone());

		WriteResult res = this.collection.insert(newTask);
		ObjectId id = (ObjectId) newTask.get("_id");

		return ((Double) res.getField("ok")).intValue() == 1 ? id.toString()
				: null;
	}

	public Task getTaskById(String id) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBObject item = collection.findOne(query);
		Task t = new Task();
		t.setTitle(item.get("title").toString());
		t.setBody(item.get("body").toString());
		t.setDone(Boolean.getBoolean(item.get("done").toString()));
		t.setId(item.get("_id").toString());
		return t;
	}

	public int update(Task t) {
		BasicDBObject newTask = new BasicDBObject();
		newTask.put("title", t.getTitle());
		newTask.put("body", t.getBody());
		newTask.put("done", t.isDone());

		BasicDBObject searchQuery = new BasicDBObject().append("_id",
				new ObjectId(t.getId()));
		WriteResult res = this.collection.update(searchQuery, newTask);
		return res.getN();
	}

	public int updateDone(Task t) {
		BasicDBObject searchQuery = new BasicDBObject().append("_id",new ObjectId(t.getId()));

		BasicDBObject setQuery = new BasicDBObject();
		setQuery.put("done", t.isDone());
		BasicDBObject set = new BasicDBObject("$set", setQuery);
		WriteResult res = this.collection.update(searchQuery, set);
		return res.getN();
	}

}
