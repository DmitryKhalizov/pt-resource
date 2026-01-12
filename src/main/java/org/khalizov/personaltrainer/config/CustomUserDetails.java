package org.khalizov.personaltrainer.config;

import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.model.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails, Serializable {

    private final User user;
    private final PersonalTrainer trainer;

    public CustomUserDetails(User user) {
        this.user = user;
        this.trainer = null;
    }


    public CustomUserDetails(PersonalTrainer trainer) {
        this.trainer = trainer;
        this.user = null;
    }

    public User getUser() {
        return user;
    }

    public PersonalTrainer getTrainer() {
        return trainer;
    }


    public boolean isTrainer() {
        return trainer != null;
    }


    public User getEffectiveUser() {
        if (trainer != null) {
            User synthetic = new User();
            synthetic.setUserId(trainer.getTrainerId()); // or null
            synthetic.setFirstName(trainer.getFirstName());
            synthetic.setLastName(trainer.getLastName());
            synthetic.setEmail(trainer.getEmail());
            synthetic.setPhone(trainer.getPhone());
            synthetic.setStatus(trainer.getStatus());
            synthetic.setUserType(UserType.TRAINER);
            return synthetic;
        }
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For simplicity: assign ROLE_USER or ROLE_TRAINER
        if (isTrainer()) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_TRAINER"));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        if (isTrainer()) {
            return trainer.getPasswordHash();
        } else {
            return user.getPasswordHash();
        }
    }

    @Override
    public String getUsername() {
        return isTrainer() ? trainer.getNickname() : user.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() {
        Status status = isTrainer() ? trainer.getStatus() : user.getStatus();
        return status == Status.ACTIVE;
    }
}

