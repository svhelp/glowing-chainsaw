package serv;

import crud.SubTaskService;
import crud.TaskService;
import entity.SubTask;
import entity.Task;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/todos")
public class MainServlet{

    @EJB
    private TaskService ts;

    @EJB
    private SubTaskService sts;

    @GET
    @Path("/")
    public Response printAll(){
        List<Task> tasks = ts.getAll();
        return Response.ok(tasks, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/query")
    public Response printPage(@QueryParam("page") int page, @QueryParam("size") int size, @DefaultValue("0")@QueryParam("offset") int offset, @QueryParam("done") boolean done, @QueryParam("undone") boolean undone, @QueryParam("left") boolean left){
        if (left==true){
            return Response.ok(ts.getCount(), MediaType.APPLICATION_JSON).build();
        }

        List<Task> tasks = new ArrayList<Task>();
        if (done==true) {
            tasks = ts.getSpecPage(page, size, offset, true);
        }
        if (undone==true) {
            tasks = ts.getSpecPage(page, size, offset, false);
        }
        if (done==false && undone==false) {
            tasks = ts.getPage(page, size, offset);
        }

        return Response.ok(tasks, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{param}")
    public Response printOne(@PathParam("param") Integer id) {
        Task gotTask = ts.get(id);
        return Response.ok(gotTask, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{param}/subs")
    public Response printSubs(@PathParam("param") int id){
        List<SubTask> subs = new ArrayList<SubTask>();
        subs = sts.getSubsFrom(ts.get(id));
        return Response.ok(subs, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{param}")
    public void removeTodo(@PathParam("param") Integer id) {
        ts.delete(id);
    }

    @DELETE
    @Path("/{param}/subs/{id}")
    public void removeSub(@PathParam("param") Integer idSuper, @PathParam("id") int idSub){
        sts.delete(idSub);
    }

    @POST
    @Path("/")
    public Response addTodo(Task json){
        Task task = ts.add(json);
        return Response.ok(task, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/{param}/subs/")
    public Response addSub(@PathParam("param") int id, SubTask json){
        json.setSupertodo(ts.get(id));
        SubTask subTask = sts.add(json);
        return Response.ok(subTask, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{param}")
    @Consumes("application/json")
    public void updTodo(@PathParam("param") Integer id, Task json){
        Task task = ts.get(id);
        task.switchDone();
        ts.update(task);
    }

    @PUT
    @Path("/{param}/subs/{id}")
    @Consumes("application/json")
    public void updSub(@PathParam("param") Integer idSuper, @PathParam("id") int idSub, Task json){
        SubTask subTask = sts.get(idSub);
        subTask.switchDone();
        sts.update(subTask);
    }
}
