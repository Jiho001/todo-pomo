package com.pomodoro.pomo.domain;

import com.pomodoro.pomo.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Todo {

    @Id @GeneratedValue
    @Column(name="todo_id")
    private Long id;

    @OneToOne(mappedBy = "todo")
    private Category category;

    public void changeCategory(Category category){
        this.category = category;
        category.setTodo(this);
    }

    private String task;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private LocalDateTime deadline;

    private LocalDateTime createdDate;
    
    public void setDate(LocalDateTime dead, LocalDateTime created) {
        this.deadline = dead;
        this.createdDate = created;
    }

    public void createTask(String task){
        this.task = task;
    }

    public void changeStatus(TaskStatus status){
        this.taskStatus = status;
    }
}
