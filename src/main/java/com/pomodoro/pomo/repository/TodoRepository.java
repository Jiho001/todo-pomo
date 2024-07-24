package com.pomodoro.pomo.repository;

import com.pomodoro.pomo.domain.Todo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final EntityManager em;

    public void save(Todo todo) {
        em.persist(todo);
    }

    public Todo findOne(Long id) {
        return em.find(Todo.class, id);
    }

    public List<Todo> findByOption(TodoSearch todosearch) {
        return em.createQuery("select t from Todo t join t.category c" +
                        " where t.taskStatus = :status" +
                        " and t.deadline = :deadline" +
                        " and c.name like :name", Todo.class)
                .setParameter("status", todosearch.getStatus())
                .setParameter("deadline", todosearch.getDeadline())
                .setParameter("name", todosearch.getName())
                .setMaxResults(1000)  // 최대 1000건 조회
                .getResultList();

    }

}
