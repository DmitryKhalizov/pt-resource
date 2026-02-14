package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.PersonalTrainerUpdateDTO;
import org.khalizov.personaltrainer.model.*;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.khalizov.personaltrainer.dto.PersonalTrainerCreateDTO;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.mapper.PersonalTrainerDTOMapper;
import org.khalizov.personaltrainer.repository.LocationRepository;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalTrainerService {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;
    @Autowired
    private PersonalTrainerDTOMapper personalTrainerDTOMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<PersonalTrainerDTO> getAllTrainers() {
        return personalTrainerRepository.findAllWithCollections()
                .stream()
                .map(personalTrainerDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<PersonalTrainerDTO> getTrainerById(Integer id) {
        return personalTrainerRepository.findById(id)
                .map(personalTrainerDTOMapper);
    }

    public Optional<PersonalTrainerDTO> getTrainerByNickname(String nickname) {
        return personalTrainerRepository.findByNickname(nickname)
                .map(personalTrainerDTOMapper);
    }

    public List<PersonalTrainerDTO> getTrainerByStatus(Status status) {
        return personalTrainerRepository.findByStatus(status)
                .stream()
                .map(personalTrainerDTOMapper)
                .toList();
    }

    public PersonalTrainerDTO createTrainer(PersonalTrainerCreateDTO dto) {
        personalTrainerRepository.findByNickname(dto.getNickname()).ifPresent(trainer -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nickname already exists");
        });

        userRepository.findByNickname(dto.getNickname()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nickname already exists");
        });

        PersonalTrainer trainer = new PersonalTrainer();
        trainer.setProfileImage(dto.getProfileImage());
        trainer.setProfileImageType((dto.getProfileImageType()));
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setNickname(dto.getNickname());
        trainer.setEmail(dto.getEmail());
        trainer.setDescription(dto.getDescription());
        trainer.setSport(dto.getSport());
        trainer.setExperienceYears(dto.getExperienceYears());
        trainer.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE);
        trainer.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        Price price = new Price();
        price.setPricePerHour(dto.getPricePerHour());
        price.setPriceFiveHours(dto.getPriceFiveHours());
        price.setPriceTenHours(dto.getPriceTenHours());
        price.setSpecialPrice(dto.getSpecialPrice());
        trainer.setPrice(price);

        if(dto.getLocationId() != null) {
            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Location with id " + dto.getLocationId() + " not found"));
            trainer.getLocations().add(location);
        }
        try {
            PersonalTrainer saved = personalTrainerRepository.save(trainer);
            return personalTrainerDTOMapper.apply(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
    }

    @Transactional
    public PersonalTrainerDTO addLocationToTrainer(Integer trainerId, Integer locationId) {
        PersonalTrainer trainer = personalTrainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Trainer with id " + trainerId + " not found"));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Location with id " + locationId + " not found"));

        trainer.getLocations().add(location);
        PersonalTrainer saved = personalTrainerRepository.save(trainer);

        return personalTrainerDTOMapper.apply(saved);
    }

    @Transactional
    public PersonalTrainerDTO removeLocationFromTrainer(Integer trainerId, Integer locationId) {
        PersonalTrainer trainer = personalTrainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Trainer with id " + trainerId + " not found"));

        trainer.getLocations().removeIf(location -> location.getLocationId().equals(locationId));
        PersonalTrainer saved = personalTrainerRepository.save(trainer);

        return personalTrainerDTOMapper.apply(saved);
    }

    @Transactional
    public PersonalTrainerDTO updateTrainer(Integer trainerId, PersonalTrainerUpdateDTO dto) {
        PersonalTrainer trainer = personalTrainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Trainer with id " + trainerId + " not found"));

        if (dto.getProfileImage() != null) {
            trainer.setProfileImage(dto.getProfileImage());
            trainer.setProfileImageType(dto.getProfileImageType());
        }
        if (dto.getFirstName() != null) {
            trainer.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            trainer.setLastName(dto.getLastName());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            trainer.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getNickname() != null) {
            if (!trainer.getNickname().equals(dto.getNickname())) {
                personalTrainerRepository.findByNickname(dto.getNickname())
                        .ifPresent(existingTrainer -> {
                            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                    "Nickname already exists");
                        });
            }
            trainer.setNickname(dto.getNickname());
        }
        if (dto.getEmail() != null) {
            trainer.setEmail(dto.getEmail());
        }
        if (dto.getDescription() != null) {
            trainer.setDescription(dto.getDescription());
        }
        if (dto.getSport() != null) {
            trainer.setSport(dto.getSport());
        }
        if (dto.getExperienceYears() != null) {
            trainer.setExperienceYears(dto.getExperienceYears());
        }
        if (dto.getStatus() != null) {
            trainer.setStatus(dto.getStatus());
        }


        if (dto.getLocationId() != null) {
            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Location with id " + dto.getLocationId() + " not found"));
            trainer.getLocations().clear();
            trainer.getLocations().add(location);
        }


        Price price = trainer.getPrice();
        if (price != null) {
            if (dto.getPricePerHour() != null) {
                price.setPricePerHour(dto.getPricePerHour());
            }
            if (dto.getPriceFiveHours() != null) {
                price.setPriceFiveHours(dto.getPriceFiveHours());
            }
            if (dto.getPriceTenHours() != null) {
                price.setPriceTenHours(dto.getPriceTenHours());
            }
            if (dto.getSpecialPrice() != null) {
                price.setSpecialPrice(dto.getSpecialPrice());
            }
        }

        PersonalTrainer saved = personalTrainerRepository.save(trainer);
        return personalTrainerDTOMapper.apply(saved);
    }

    public List<PersonalTrainerDTO> getTrainersBySport(Sport sport) {
        return personalTrainerRepository.findBySport(sport).stream()
                .map(personalTrainerDTOMapper)
                .toList();
    }

    public void deleteTrainer(Integer id) {

            personalTrainerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PersonalTrainerDTO> findAllUnsorted() {
        return personalTrainerRepository.findAll()
                .stream()
                .map(personalTrainerDTOMapper)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PersonalTrainerDTO> findAllSorted(Sort sort) {
        return personalTrainerRepository.findAll(sort)
                .stream()
                .map(personalTrainerDTOMapper)
                .toList();
    }
}