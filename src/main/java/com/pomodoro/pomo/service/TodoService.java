package com.pomodoro.pomo.service;

import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.dto.TodoDto;
import com.pomodoro.pomo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public Todo findTodo(Long id) {
        return todoRepository.findOne(id);
    }

    @Transactional
    public void editTodo(Todo todo, Long id, TodoDto dto) {
        todo.setId(id);
        todo.createTask(dto.getName());
        todo.changeStatus(dto.getStatus());
        todo.setDate(dto.getDueDate());
        for (Category category : dto.getCategories()) {
            todo.addCategory(category);
        }

        todoRepository.save(todo);
    }

    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }
}
