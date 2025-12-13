package org.khalizov.personaltrainer;

import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.khalizov.personaltrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyMain implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalTrainerService personalTrainerService;



    @Override
    public void run(String... args) {



    }

}
