package com.pomodoro.pomo.domain;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "todo_id")
    @Setter
    private Todo todo;

    private String name;


}
