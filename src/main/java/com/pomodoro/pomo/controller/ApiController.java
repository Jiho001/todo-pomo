package com.pomodoro.pomo.controller;

import com.pomodoro.pomo.TaskStatus;
import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final TodoService todoService;
    @PostMapping("/api/todos")
    public TodoResponse addTodo(@RequestBody TodoRequest request) {
        Todo todo = Todo.createTodo(request.getName(), request.getDueDate(),
                request.getStatus());
        List<Category> categories = request.getCategories();
        categories.forEach(todo::addCategory);

        todoService.saveTodo(todo);
        Todo findTodo = todoService.findTodo(todo.getId());
        return new TodoResponse(findTodo.getName(), findTodo.getStatus(),
                findTodo.getDueDate(), findTodo.getCategories());
    }

    @GetMapping("/api/todos/edit/{todoId}")
    public TodoResponse editTodo(@PathVariable("todoId") Long todoId,
                                 @RequestBody TodoRequest request) {
        Todo todo =
    }

    @Data
    static class TodoRequest {
        private String name;
        private TaskStatus status;
        private LocalDate dueDate;
        private List<Category> categories;
    }

    @Data
    @AllArgsConstructor
    static class TodoResponse {
        private String name;
        private TaskStatus status;
        private LocalDate dueDate;
        private List<Category> categories;
    }
}
