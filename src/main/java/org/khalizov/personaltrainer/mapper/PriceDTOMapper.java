package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.model.Price;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PriceDTOMapper implements Function<Price, PriceDTO> {
    @Override
    public PriceDTO apply(Price price) {
        return new PriceDTO(
                price.getPriceId(),
                price.getPricePerHour(),
                price.getPriceFiveHours(),
                price.getPriceTenHours(),
                price.getSpecialPrice()
        );
    }
}
