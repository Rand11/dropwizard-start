package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.api.Food;
import com.example.helloworld.db.FoodDao;
import io.dropwizard.hibernate.UnitOfWork;
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

//TODO: get rid of the dummy findFood() method
//    @GET
//    @Timed
//    @UnitOfWork
//    public Food findFood() {
//        return new Food(counter.incrementAndGet(), "banana", 6.66, 999);
//    }

    @GET
    @Timed
    @UnitOfWork
    public Food findFood(@PathParam("id") LongParam id) {
        return foodDao.findById(id.get());
    }

/*  @POST
    @Timed
    @UnitOfWork
    public long createFood(Food food) {
        return foodDao.create(food);
    }*/

    @POST
    @Timed
    @UnitOfWork
    public long createFood(@FormParam("name") String name,
                          @FormParam("quantity") double quantity,
                          @FormParam("price") int price) {
        return foodDao.create(new Food(name, quantity, price));
    }

}
