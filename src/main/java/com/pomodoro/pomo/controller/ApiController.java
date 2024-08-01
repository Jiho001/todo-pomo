package com.pomodoro.pomo.controller;

import com.pomodoro.pomo.TaskStatus;
import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.dto.TodoDto;
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
    public TodoDto addTodo(@RequestBody TodoDto request) {
        Todo todo = Todo.createTodo(request.getName(), request.getDueDate(),
                request.getStatus());
        List<Category> categories = request.getCategories();
        categories.forEach(todo::addCategory);

        todoService.saveTodo(todo);
        Todo findTodo = todoService.findTodo(todo.getId());
        return new TodoDto(findTodo.getName(), findTodo.getStatus(),
                findTodo.getDueDate(), findTodo.getCategories());
    }

    @GetMapping("/api/todos/edit/{todoId}")
    public TodoDto editTodo(@PathVariable("todoId") Long todoId,
                                 @RequestBody TodoDto request) {
        Todo todo = new Todo();
        todoService.editTodo(todo, todoId, request);

        Todo findTodo = todoService.findTodo(todo.getId());
        return new TodoDto(findTodo.getName(), findTodo.getStatus(),
                findTodo.getDueDate(), findTodo.getCategories());
    }

}
