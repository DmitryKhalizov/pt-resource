package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.PriceCreateDTO;
import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.mapper.PriceDTOMapper;
import org.khalizov.personaltrainer.model.Price;
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

    public List<PriceDTO> findByPricePerHourBetween(BigDecimal min, BigDecimal max) {
        return priceRepository.findByPricePerHourBetween(min, max)
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findByPriceFiveHoursBetween(BigDecimal min, BigDecimal max) {
        return priceRepository.findByPriceFiveHoursBetween(min, max)
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findByPriceTenHoursBetween(BigDecimal min, BigDecimal max) {
        return priceRepository.findByPriceTenHoursBetween(min, max)
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findAllOrderByPricePerHourAsc() {
        return priceRepository.findAllByOrderByPricePerHourAsc()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findAllOrderByPricePerHourDesc() {
        return priceRepository.findAllByOrderByPricePerHourDesc()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findAllOrderByPriceFiveHoursAsc() {
        return priceRepository.findAllByOrderByPriceFiveHoursAsc()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findAllOrderByPriceFiveHoursDesc() {
        return priceRepository.findAllByOrderByPriceFiveHoursDesc()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findAllOrderByPriceTenHoursAsc() {
        return priceRepository.findAllByOrderByPriceTenHoursAsc()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> findAllOrderByPriceTenHoursDesc() {
        return priceRepository.findAllByOrderByPriceTenHoursDesc()
                .stream()
                .map(priceDTOMapper)
                .toList();
    }

    public List<PriceDTO> getPricesBySport(String sport) {
        return priceRepository.findAll()
                .stream()
                .filter(price -> price.getTrainer() != null && sport.equalsIgnoreCase(price.getTrainer().getSport().toString()))
                .map(priceDTOMapper)
                .toList();
    }

    private Price getPriceOrThrow(Integer id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Price with id " + id + " not found"));
    }
}
