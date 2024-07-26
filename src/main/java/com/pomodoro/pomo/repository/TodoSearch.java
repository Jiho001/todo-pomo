package com.pomodoro.pomo.repository;

import com.pomodoro.pomo.TaskStatus;
import com.pomodoro.pomo.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TodoSearch {

    private Category name;
    private TaskStatus status;
    private LocalDateTime dueDate;

}
