package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.PriceCreateDTO;
import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.mapper.PriceDTOMapper;
import org.khalizov.personaltrainer.model.Price;
import org.khalizov.personaltrainer.model.Sport;
import org.khalizov.personaltrainer.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private PriceDTOMapper priceDTOMapper;

    public List<PriceDTO> getAllPrices() {
        return priceRepository.findAll()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public PriceDTO getById(Integer id) {
        return priceRepository.findById(id)
                .map(priceDTOMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Price with id " + id + " not found"));
    }

    public PriceDTO createPrice(PriceCreateDTO dto) {
        Price price = new Price();
        price.setPricePerHour(dto.getPricePerHour());
        price.setPriceFiveHours(dto.getPriceFiveHours());
        price.setPriceTenHours(dto.getPriceTenHours());
        price.setSpecialPrice(dto.getSpecialPrice());

        try {
            Price saved = priceRepository.save(price);
            return priceDTOMapper.apply(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
    }

    public PriceDTO updatePrice(Integer id, PriceCreateDTO dto) {
        Price price = getPriceOrThrow(id);
        price.setPricePerHour(dto.getPricePerHour());
        price.setPriceFiveHours(dto.getPriceFiveHours());
        price.setPriceTenHours(dto.getPriceTenHours());
        price.setSpecialPrice(dto.getSpecialPrice());
        try {
            Price saved = priceRepository.save(price);
            return priceDTOMapper.apply(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
    }

    public void deletePrice(Integer id) {
        Price price = getPriceOrThrow(id);
        priceRepository.delete(price);
    }

    public List<PriceDTO> getPricesBySportEnum(Sport sport) {

        return priceRepository.findAll()
                .stream()
                .filter(price -> price.getTrainer() != null && sport == price.getTrainer().getSport())
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findByRangeAndSport(String field, BigDecimal min, BigDecimal max, Sport sport) {
        switch (normalizeField(field)) {
            case HOUR -> {
                if (sport != null) {
                    return priceRepository.findByPricePerHourBetweenAndTrainer_Sport(min, max, sport)
                            .stream().map(priceDTOMapper).toList();
                }
                return priceRepository.findByPricePerHourBetween(min, max)
                        .stream().map(priceDTOMapper).toList();
            }
            case FIVE -> {
                if (sport != null) {
                    return priceRepository.findByPriceFiveHoursBetweenAndTrainer_Sport(min, max, sport)
                            .stream().map(priceDTOMapper).toList();
                }
                return priceRepository.findByPriceFiveHoursBetween(min, max)
                        .stream().map(priceDTOMapper).toList();
            }
            case TEN -> {
                if (sport != null) {
                    return priceRepository.findByPriceTenHoursBetweenAndTrainer_Sport(min, max, sport)
                            .stream().map(priceDTOMapper).toList();
                }
                return priceRepository.findByPriceTenHoursBetween(min, max)
                        .stream().map(priceDTOMapper).toList();
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown field: " + field);
        }
    }

    // Consolidated sort handler
    public List<PriceDTO> findAllSortedByFieldAndSport(String field, String order, Sport sport) {
        boolean asc = normalizeOrder(order);
        switch (normalizeField(field)) {
            case HOUR -> {
                if (sport != null) {
                    return (asc ? priceRepository.findAllByTrainer_SportOrderByPricePerHourAsc(sport)
                            : priceRepository.findAllByTrainer_SportOrderByPricePerHourDesc(sport))
                            .stream().map(priceDTOMapper).toList();
                }
                return (asc ? priceRepository.findAllByOrderByPricePerHourAsc()
                        : priceRepository.findAllByOrderByPricePerHourDesc())
                        .stream().map(priceDTOMapper).toList();
            }
            case FIVE -> {
                if (sport != null) {
                    return (asc ? priceRepository.findAllByTrainer_SportOrderByPriceFiveHoursAsc(sport)
                            : priceRepository.findAllByTrainer_SportOrderByPriceFiveHoursDesc(sport))
                            .stream().map(priceDTOMapper).toList();
                }
                return (asc ? priceRepository.findAllByOrderByPriceFiveHoursAsc()
                        : priceRepository.findAllByOrderByPriceFiveHoursDesc())
                        .stream().map(priceDTOMapper).toList();
            }
            case TEN -> {
                if (sport != null) {
                    return (asc ? priceRepository.findAllByTrainer_SportOrderByPriceTenHoursAsc(sport)
                            : priceRepository.findAllByTrainer_SportOrderByPriceTenHoursDesc(sport))
                            .stream().map(priceDTOMapper).toList();
                }
                return (asc ? priceRepository.findAllByOrderByPriceTenHoursAsc()
                        : priceRepository.findAllByOrderByPriceTenHoursDesc())
                        .stream().map(priceDTOMapper).toList();
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown field: " + field);
        }
    }

    private Price getPriceOrThrow(Integer id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Price with id " + id + " not found"));
    }

    private enum Field { HOUR, FIVE, TEN }

    private Field normalizeField(String field) {
        if (field == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "field is required");
        return switch (field.toLowerCase()) {
            case "hour", "perhour", "priceperhour" -> Field.HOUR;
            case "five", "fivehours", "pricefivehours" -> Field.FIVE;
            case "ten", "tenhours", "pricetenhours" -> Field.TEN;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown field: " + field);
        };
    }

    private boolean normalizeOrder(String order) {
        if (order == null) return true; // default asc
        return switch (order.toLowerCase()) {
            case "asc" -> true;
            case "desc" -> false;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown order: " + order);
        };
    }
}
