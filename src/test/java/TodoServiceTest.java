import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;
import springboot.service.TodoService;

import java.util.ArrayList;
import java.util.List;

    public class TodoServiceTest {
        @InjectMocks
        private TodoService todoService;

        @Mock
        private TodoRepository todoRepository;

        @Before
        public void setUp(){
            /*//instatiate TodoService
            this.todoRepository = new TodoRepository();*/
            MockitoAnnotations.initMocks(this);
            this.todoService = new TodoService(this.todoRepository);
        }

        @After
        public void tearDown(){
            //missing ? (mastiin ga manggil function lain)
            Mockito.verifyNoMoreInteractions();
        }

        @Test
        public void getAllTest() throws Exception{

            //given
            //todo repo must return non empty list when getAll is called
            ArrayList<Todo> todos = new ArrayList<Todo>();
            todos.add(new Todo("todo1", TodoPriority.LOW));

            BDDMockito.given(todoRepository.getAll()).willReturn(todos);

            //prep data
            //todoRepository.store(new Todo("todo1", TodoPriority.LOW));

            //when
            List<Todo> todoList = todoService.getAll();

            //then
            //assert that todo list is not null
            Assert.assertThat(todoList, Matchers.notNullValue());

            //assert that todo list is not empty
            Assert.assertThat(todoList.isEmpty(), Matchers.equalTo(false));

            //verify
            BDDMockito.then(todoRepository).should().getAll();


//            if(todoList.isEmpty()){
//                throw new Exception("list kosong");
//            }
//            else{
//                System.out.println("list ada isinya");
//            }

        }

        @Test
        public void saveTodoTest() {
            // Given
            Todo newTodo = new Todo("Test insert new todo item.", TodoPriority.LOW);
            BDDMockito.given(this.todoRepository.store(newTodo)).willReturn(true);

            // When
            boolean success = todoService.saveTodo(newTodo.getName(), newTodo.getPriority());

            // Then
            Assert.assertTrue(success);

            // Verify
            Mockito.verify(this.todoRepository, Mockito.times(1))
                    .store(newTodo);
        }
    }


