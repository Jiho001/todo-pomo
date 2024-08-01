package com.pomodoro.pomo.dto;

import com.pomodoro.pomo.TaskStatus;
import com.pomodoro.pomo.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
public class TodoDto {

        private String name;
        private TaskStatus status;
        private LocalDate dueDate;
        private List<Category> categories;

}
