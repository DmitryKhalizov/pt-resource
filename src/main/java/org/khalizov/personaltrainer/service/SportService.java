package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.model.Sports;
import org.khalizov.personaltrainer.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public List<Sports> getAllSports() {
        return sportRepository.findAll();
    }

    public Optional<Sports> getSportById(Integer id) {
        return sportRepository.findById(id);
    }

    public Sports saveSport(Sports sport) {
        return sportRepository.save(sport);
    }

    public void deleteSport(Integer id) {
        sportRepository.deleteById(id);
    }
}