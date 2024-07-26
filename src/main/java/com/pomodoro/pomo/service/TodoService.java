package com.pomodoro.pomo.service;

import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
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
    public void saveTodo(Todo todo){
        todoRepository.save(todo);
    }

    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }
}
