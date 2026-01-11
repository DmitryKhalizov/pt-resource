package org.khalizov.personaltrainer.config;

import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String normalizedEmail = email.trim().toLowerCase();
        System.out.println("DEBUG: searching for user " + normalizedEmail);

        Optional<PersonalTrainer> trainerOptional = personalTrainerRepository.findByEmail(normalizedEmail);
        if(trainerOptional.isPresent()) {
            PersonalTrainer trainer = trainerOptional.get();
            System.out.println("DEBUG: trainer found " + trainer.getEmail());
            return org.springframework.security.core.userdetails.User.builder()
                    .username(trainer.getEmail())
                    .password(trainer.getPasswordHash())
                    .roles("TRAINER")
                    .build();
        }

        Optional<User> userOptional = userRepository.findByEmail(normalizedEmail);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("DEBUG: user found " + user.getEmail());
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPasswordHash())
                    .roles(user.getUserType().name())
                    .build();
        }

        throw new UsernameNotFoundException("User not found " + normalizedEmail);

    }
}