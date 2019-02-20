package com.example.demo.persistance;


import com.example.demo.model.Event;
import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, String> {
}

