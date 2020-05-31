package net.thumbtack.asurovenko;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class TodoTaskTest extends JerseyTest {
    private Gson gson = new GsonBuilder().create();

    @Override
    protected Application configure() {
        return new MyApplication();
    }

    /*@Before
    public void raiseServer() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        MyApplication config = new MyApplication();
        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
    }*/

    @Test
    public void testAddTask() {
        TodoTask newTask = new TodoTask("Task description - addTask", true);

        TodoTask t = given()
                .body(gson.toJson(newTask))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        assertEquals(true, t.isDone());
        assertEquals("Task description - addTask", t.getDescription());
        assertTrue(t.getId() != 0);
    }

    @Test
    public void testEditTask() {
        TodoTask addTask = given()
                .body(gson.toJson(new TodoTask("Task description - editTask", false)))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        TodoTask changeTask = given()
                .queryParam("description", "Change description")
                .queryParam("done", true)
                .when()
                .post("http://localhost:9998/task/" + String.valueOf(addTask.getId()))
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        assertEquals(true, changeTask.isDone());
        assertEquals("Change description", changeTask.getDescription());
        assertTrue(changeTask.getId() == addTask.getId());

    }

    @Test
    public void testGetTask() {
        TodoTask addTask = given()
                .body(gson.toJson(new TodoTask("Task description - addTask", true)))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        TodoTask newTask = given()
                .when()
                .get("http://localhost:9998/task/" + String.valueOf(addTask.getId()))
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        assertEquals(true, newTask.isDone());
        assertEquals("Task description - addTask", newTask.getDescription());
        assertTrue(newTask.getId() == addTask.getId());
    }

    @Test
    public void testDeleteTask() {
        TodoTask task = given()
                .body(gson.toJson(new TodoTask("des 1", true)))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        given()
                .when()
                .delete("http://localhost:9998/task/" + String.valueOf(task.getId()))
                .then()
                .statusCode(200);

        given()
                .when()
                .get("http://localhost:9998/task/" + String.valueOf(task.getId()))
                .then()
                .statusCode(404);

    }

    @Test
    public void testDeleteAllTask() {

        TodoTask task1 = given()
                .body(gson.toJson(new TodoTask("des 1", true)))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        TodoTask task2 = given()
                .body(gson.toJson(new TodoTask("des 22", false)))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);

        TodoTask task3 = given()
                .body(gson.toJson(new TodoTask("des 333", true)))
                .contentType(ContentType.JSON)
                .when()
                .post("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .as(TodoTask.class);


        given()
                .when()
                .delete("http://localhost:9998/task/")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("http://localhost:9998/task/" + String.valueOf(task1.getId()))
                .then()
                .statusCode(404);

        given()
                .when()
                .get("http://localhost:9998/task/" + String.valueOf(task2.getId()))
                .then()
                .statusCode(404);

        given()
                .when()
                .get("http://localhost:9998/task/" + String.valueOf(task3.getId()))
                .then()
                .statusCode(404);

    }

    @Test
    public void testGetAllTask() {
        TodoTask task1 = new TodoTask("description 1", true);
        TodoTask task2 = new TodoTask("description 22", false);
        TodoTask task3 = new TodoTask("description 333", true);

        List<TodoTask> list = new ArrayList<>();

        list.add(
                given()
                        .body(gson.toJson(task1))
                        .contentType(ContentType.JSON)
                        .when()
                        .post("http://localhost:9998/task/")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(TodoTask.class));
        list.add(
                given()
                        .body(gson.toJson(task2))
                        .contentType(ContentType.JSON)
                        .when()
                        .post("http://localhost:9998/task/")
                        .then()
                        .statusCode(200).extract()
                        .as(TodoTask.class));

        list.add(
                given()
                        .body(gson.toJson(task3))
                        .contentType(ContentType.JSON)
                        .when()
                        .post("http://localhost:9998/task/")
                        .then()
                        .statusCode(200).extract()
                        .as(TodoTask.class));

        String strList = given()
                .body(gson.toJson(task3))
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:9998/task/")
                .then()
                .statusCode(200)
                .extract()
                .body().asString();

        Type type = new TypeToken<List<TodoTask>>() {
        }.getType();

        List<TodoTask> list1 = gson.fromJson(strList, type);

        assertEquals(list, list1);

    }

}
