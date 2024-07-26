package com.pomodoro.pomo.controller;

import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TodoService todoService;

    @GetMapping()
    public String home(){
        return "home";
    }

    @PostMapping("/api/todos")
    public String addTodo(@RequestBody Todo todo) {
//        for (Category category : todo.getCategories()) {
//            category.setName();
//        }
//        todoService.saveTodo(todo);

        return "redirect:/";
    }
}
