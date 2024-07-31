package com.pomodoro.pomo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TaskStatus{
    COMPLETED("완료"),
    ONGOING("진행중"),
    WAITING("대기");

    @Getter
    private final String name;
    private TaskStatus(String name){
        this.name = name;
    }
}
