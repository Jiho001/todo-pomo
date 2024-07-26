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

    public List<Todo> findAll(){
        return em.createQuery("select t from Todo t", Todo.class)
                .getResultList();
    }

    public List<Todo> findByOption(TodoSearch todosearch) {
        return em.createQuery("select t from Todo t join t.categories c" +
                        " where t.status = :status" +
                        " and t.dueDate = :dueDate" +
                        " and c.name like :name", Todo.class)
                .setParameter("status", todosearch.getStatus())
                .setParameter("dueDate", todosearch.getDueDate())
                .setParameter("name", todosearch.getName())
                .setMaxResults(1000)  // 최대 1000건 조회
                .getResultList();

    }

}
