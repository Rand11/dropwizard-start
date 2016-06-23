package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.api.Food;
import com.example.helloworld.db.FoodDao;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by adrianz on 21/06/16.
 */

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    private final AtomicLong counter;
    private final FoodDao foodDao;

    public FoodResource(FoodDao foodDao) {
        this.foodDao = foodDao;
        //TODO: get rid of counter
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    @UnitOfWork
    public Food findFood(@QueryParam("id") LongParam id) {
        return foodDao.findById(id.get());
    }

    @POST
    @Timed
    @UnitOfWork
    public long createFood(@FormParam("name") String name,
                          @FormParam("quantity") double quantity,
                          @FormParam("price") int price) {
        return foodDao.create(new Food(name, quantity, price));
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") LongParam id) {
        foodDao.delete(id.get());
    }

    @Path("/{id}")
    @PUT
    @UnitOfWork
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public boolean update(@PathParam("id") LongParam id, Food food) {
        System.out.println(" ############################### ");
        System.out.println(food.getId());
        System.out.println(food.getName());
        System.out.println(food.getPrice());
        System.out.println(" ############################### ");
        if (foodDao.exists(id.get())) {
            foodDao.update(id.get(), food);
            return true;
        } else {
            return false;
        }
    }

}
