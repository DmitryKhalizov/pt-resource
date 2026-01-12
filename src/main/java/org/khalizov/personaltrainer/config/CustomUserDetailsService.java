package org.khalizov.personaltrainer.config;

import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
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

        // Check if it's a trainer
        Optional<PersonalTrainer> trainerOptional = personalTrainerRepository.findByEmail(normalizedEmail);
        if (trainerOptional.isPresent()) {
            PersonalTrainer trainer = trainerOptional.get();
            System.out.println("DEBUG: returning CustomUserDetails for TRAINER: " + trainer.getEmail());
            return new CustomUserDetails(trainer);  // ← Use trainer constructor
        }

        // Check if it's a regular user
        Optional<User> userOptional = userRepository.findByEmail(normalizedEmail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("DEBUG: returning CustomUserDetails for USER: " + user.getEmail());
            return new CustomUserDetails(user);  // ← Use user constructor
        }

        throw new UsernameNotFoundException("User not found " + normalizedEmail);
    }
}
