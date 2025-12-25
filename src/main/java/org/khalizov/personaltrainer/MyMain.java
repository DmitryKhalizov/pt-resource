package org.khalizov.personaltrainer;

import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyMain implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyMain.class);

    @Autowired
    private PersonalTrainerRepository repository;

    @Override
    public void run(String... args) {

    }
}