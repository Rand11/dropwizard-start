package com.example.helloworld;

import com.example.helloworld.api.Food;
import com.example.helloworld.db.FoodDao;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.resources.FoodResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by adrianz on 20/06/16.
 */
public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    private final HibernateBundle<HelloWorldConfiguration> hibernate = new HibernateBundle<HelloWorldConfiguration>(Food.class) {
        public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);

        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {

        final HelloWorldResource helloResource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        final FoodDao dao = new FoodDao(hibernate.getSessionFactory());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(helloResource);
        environment.jersey().register(new FoodResource(dao));
    }

}
