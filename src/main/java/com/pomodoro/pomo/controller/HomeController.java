package com.pomodoro.pomo.controller;

import com.pomodoro.pomo.TaskStatus;
import com.pomodoro.pomo.domain.Category;
import com.pomodoro.pomo.domain.Todo;
import com.pomodoro.pomo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TodoService todoService;

    @GetMapping()
    public String home(){
        return "home";
    }

}
