package com.pomodoro.pomo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Setter @Getter
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    private String name;


}
