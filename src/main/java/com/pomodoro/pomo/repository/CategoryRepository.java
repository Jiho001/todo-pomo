package com.pomodoro.pomo.repository;

import com.pomodoro.pomo.domain.Category;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public Category findOne(Long id) {
        return em.find(Category.class, id);
    }

    public void deleteById(Long id){
        System.out.println("CategoryRepository.delete");
        Category delCategory = findOne(id);
        em.remove(delCategory);
    }

    public void deleteByE(Category category) {
        em.remove(category);
    }
}
