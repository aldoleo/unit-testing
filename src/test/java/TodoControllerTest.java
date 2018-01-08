

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.service.TodoService;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by indra.e.prasetya on 1/18/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @LocalServerPort
    private int serverPort;

    private static final String NAME = "Todo1";
    private static final TodoPriority PRIORITY = TodoPriority.HIGH;

    private static final String TODO = "{\"code\":200,\"message\":null,"
            + "\"value\":[{\"name\":\"Todo1\",\"priority\":\"HIGH\"}]}";

    @Test
    public void all() {
        when(todoService.getAll()).thenReturn(Arrays.asList(new Todo(NAME, PRIORITY)));

        given()
                .contentType("application/json")
                .when()
                .port(serverPort)
                .get("/todos")
                .then()
                .body(containsString("value"))
                .body(containsString(NAME))
                .body(equalTo(TODO))
                .statusCode(200);

        verify(todoService).getAll();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(this.todoService);
    }

}
