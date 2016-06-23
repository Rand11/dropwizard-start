package com.example.helloworld.db;

import com.example.helloworld.api.Food;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by adrianz on 21/06/16.
 */
public class FoodDao extends AbstractDAO<Food> {
    public FoodDao(SessionFactory factory) {
        super(factory);
    }

    public Food findById(Long id) {
        return get(id);
    }

    public long create(Food food) {
        return persist(food).getId();
    }

    public List<Food> findAll() {
        return list(namedQuery("com.example.helloworld.core.Food.findAll"));
    }

}
