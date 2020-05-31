package net.thumbtack.asurovenko;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/task")
public class TodoTaskResource {

    private static Gson gson = new Gson();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTask(String body) {
        TodoTask newTask = gson.fromJson(body, TodoTask.class);
        //REVU: it's rather bad practice to set some value using getter method. Create method addTask() in Data
        Data.getTodoTaskList().add(newTask);
        int countTodoTaskList = Data.getCountTodoTaskList();
        countTodoTaskList++;
        newTask.setId(countTodoTaskList);
        Data.setCountTodoTaskList(countTodoTaskList);

        return Response.ok().entity(gson.toJson(newTask)).build();
    }

    //REVU: if you know the id, then it is PUT method
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTask(@PathParam("id") int id, @QueryParam("description") String description, @QueryParam("done") boolean isDone) {
        for (TodoTask t : Data.getTodoTaskList()) {
            //REVU: that's why you need Map instead of List in Data
            if (t.getId() == id) {
                t.setDescription(description);
                t.setDone(isDone);
                return Response.ok().entity(gson.toJson(t)).build();
            }
        }
        return Response.status(404).build();
    }

    @GET
    public Response getAllTask() {
        if (Data.getTodoTaskList().size()!=0) {
            return Response.ok().entity(gson.toJson(Data.getTodoTaskList())).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") int id) {
        for (TodoTask t : Data.getTodoTaskList()) {
            if (t.getId() == id) {
                return Response.ok().entity(gson.toJson(t)).build();
            }
        }
        return Response.status(404).build();
    }

    @DELETE
    public Response deleteAllTask() {
        Data.getTodoTaskList().clear();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") int id) {
        for (TodoTask t : Data.getTodoTaskList()) {
            if (t.getId() == id) {
                Data.getTodoTaskList().remove(t);
                return Response.ok().build();
            }
        }
        return Response.ok().build();
        //return Response.status(404).build();
    }

}
