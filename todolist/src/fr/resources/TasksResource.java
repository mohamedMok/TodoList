package fr.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.dao.TaskDAO;
import fr.model.Task;

/**
 * 
 * @author Mohamed M
 *
 */
@Path("/tasks")
public class TasksResource {
	private TaskDAO dao = new TaskDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasks(){
		List<Task> tasks = dao.getAllTasks();
		if(tasks.isEmpty()){
			return Response.status(404).build();
		}else{
			return Response.ok().entity(tasks).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTaskById(@PathParam(value="id") String id){
		Task t = dao.getTaskById(id);
		if(t == null)
			return Response.status(400).entity("Les paramètres entrés sont incorrects (id inexistant ?)").build();
		else
			return Response.ok().entity(t).build();
	}
	
	@DELETE
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam(value="id") String id){
		boolean deleted = dao.deleteTask(id);
        if(!deleted){
                return Response.status(400).entity("Les paramètres entrés sont incorrects (id inexistant ?)").build();
        }else{
                return Response.ok().build();
        }
		
	}
	
	
}
