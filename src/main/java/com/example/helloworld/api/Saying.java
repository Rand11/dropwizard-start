package com.example.helloworld.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * Created by adrianz on 20/06/16.
 */
public class Saying {
    private long id;

    @Length(max=3)
    private String context;

    public Saying() {
        //deserialization
    }

    public Saying(long id, String context) {
        this.id = id;
        this.context = context;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContext() {
        return context;
    }
}
