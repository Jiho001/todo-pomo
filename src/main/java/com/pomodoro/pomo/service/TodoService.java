package com.pomodoro.pomo.service;

import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.dto.TodoDto;
import com.pomodoro.pomo.repository.CategoryRepository;
import com.pomodoro.pomo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public Todo findTodo(Long id) {
        return todoRepository.findOne(id);
    }

    @Transactional
    public Long editTodo(Long id, TodoDto dto) {
        Todo todo = todoRepository.findOne(id);
        todo.createTask(dto.getName());  // 이름 바꿈
        todo.changeStatus(dto.getStatus());
        todo.setDate(dto.getDueDate());

        List<Category> categories = todo.getCategories();
        categories.forEach(categoryRepository::deleteByE);
        dto.getCategories().forEach(todo::addCategory);

        return todo.getId();
    }

    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }
}
