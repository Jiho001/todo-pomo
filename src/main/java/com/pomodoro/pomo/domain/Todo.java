package com.pomodoro.pomo.domain;

import com.pomodoro.pomo.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.config.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Todo {

    @Id @GeneratedValue
    @Column(name="todo_id")
    private Long id;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate dueDate;

    public void addCategory(Category category){
        categories.add(category);
        category.setTodo(this);
    }

    public static Todo createTodo(String name, LocalDate dueDate, TaskStatus status) {
        Todo todo = new Todo();
        todo.createTask(name);
        todo.setDate(dueDate);
        todo.changeStatus(status);
        return todo;
    }


    public void setDate(LocalDate due) {
        this.dueDate = due;
    }

    public void createTask(String task){
        this.name = task;
    }

    public void changeStatus(TaskStatus status){
        this.status = status;
    }
}
