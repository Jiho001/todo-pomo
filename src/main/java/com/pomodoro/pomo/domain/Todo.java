package com.pomodoro.pomo.domain;

import com.pomodoro.pomo.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Todo {

    @Id @GeneratedValue
    @Column(name="todo_id")
    private Long id;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<Category> categories;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime dueDate;

    public void changeCategory(Category category){
        this.categories.add(category);
        category.setTodo(this);
    }

    public void setDate(LocalDateTime dead, LocalDateTime created) {
        this.dueDate = dead;
    }

    public void createTask(String task){
        this.name = task;
    }

    public void changeStatus(TaskStatus status){
        this.status = status;
    }
}
