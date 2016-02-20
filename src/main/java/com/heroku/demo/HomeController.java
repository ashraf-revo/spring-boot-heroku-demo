package com.heroku.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class HomeController {

    private RecordRepository repository;

    @Autowired
    public HomeController(RecordRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Record> home() {
        repository.save(new Record(UUID.randomUUID().toString()));
        return repository.findAll();
    }
}
