package com.pomodoro.pomo.controller;

import com.pomodoro.pomo.TaskStatus;
import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final TodoService todoService;
    @PostMapping("/api/todos")
    public CreateTodoResponse addTodo(@RequestBody CreateTodoRequest request) {
        Todo todo = Todo.createTodo(request.getName(), request.getDueDate(),
                request.getTaskStatus());
        List<Category> categories = request.getCategories();
        categories.stream().forEach(category -> todo.addCategory(category));

        todoService.saveTodo(todo);
        return new CreateTodoResponse(todo.getName());
    }

    @Data
    static class CreateTodoRequest {
        private String name;
        private TaskStatus taskStatus;
        private LocalDate dueDate;
        private List<Category> categories;
    }

    @Data
    @AllArgsConstructor
    static class CreateTodoResponse {
        private String name;
    }
}
