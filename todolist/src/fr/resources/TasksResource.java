package fr.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.twilio.sdk.TwilioRestException;

import fr.dao.TaskDAO;
import fr.model.Task;
import fr.services.TwilioService;

/**
 * 
 * @author Mohamed M
 *
 */
@Path("/tasks")
public class TasksResource {
	private TaskDAO dao = new TaskDAO();
	private TwilioService twilio = new TwilioService();

	
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
			return Response.status(404).entity("Ressource not avalaible").build();
		else
			return Response.ok().entity(t).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTask(Task t, @Context UriInfo info) throws URISyntaxException{
		if(t == null){
			return Response.status(404).entity("Syntax error").build();
		}else{
			String res = dao.create(t);
			if(res == null){
				return Response.status(409).entity("Conflict : Unable to add task").build();
			}
			else{
				return Response.created(new URI(info.getAbsolutePath() + "/" + res )).build();
			}
		}
	}
	
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam(value="id")String id, Task t){
		if(t == null){
			return Response.status(400).entity("Syntax error").build();
		}else{
			t.setId(id);
			int nb = dao.update(t);
			if(nb == 1){
				return Response.noContent().build();
				
			}else
				return Response.status(404).build();
		}
	}
	
	@PUT
	@Path("/{id}/done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDone(@PathParam(value="id")String id, Task t){
		if(t == null){
			return Response.status(400).entity("Syntax error").build();
		}else{
			t.setId(id);
			int nb = dao.updateDone(t);
			if(nb == 1){
				try {
					if(t.isDone())
						this.twilio.SendSMS(t);
				} catch (TwilioRestException e) {
					e.printStackTrace();
				}
				return Response.ok().build();
			}else
				return Response.status(404).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
    public Response deleteTask(@PathParam(value="id") String id){
		Task t = dao.getTaskById(id);
		if(t == null){
			return Response.status(404).entity("Unable to delete task").build();
		}
		int nbRes = dao.deleteTask(id);
        if(nbRes < 1 ){
                return Response.status(400).entity("Unable to delete task").build();
        }else{
                return Response.ok().build();
        }
	}	
}
