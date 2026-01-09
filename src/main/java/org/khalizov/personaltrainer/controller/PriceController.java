package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.PriceCreateDTO;
import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.model.Sport;
import org.khalizov.personaltrainer.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Price management", description = "API for managing prices")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping
    @Operation(summary = "Get all prices", description = "Fetching a list of all prices")
    public List<PriceDTO> getAllPrices() {
        return priceService.getAllPrices();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get price by ID", description = "Fetch a specific price by its ID")
    public PriceDTO getPriceById(@PathVariable @Parameter(description = "Price ID") Integer id) {
        return priceService.getById(id);
    }

    @GetMapping("/sport")
    @Operation(summary = "Get all prices by sport", description = "Fetch all prices for trainers of a specific sport")
    public List<PriceDTO> getPricesBySport(@RequestParam @Parameter(description = "Sport enum value") Sport sport) {
        return priceService.getPricesBySportEnum(sport);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new price", description = "Create a new price entry")
    public PriceDTO createPrice(@Valid @RequestBody PriceCreateDTO dto) {
        return priceService.createPrice(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a price", description = "Update an existing price by ID")
    public PriceDTO updatePrice(@PathVariable @Parameter(description = "Price ID") Integer id,
                                @Valid @RequestBody PriceCreateDTO dto) {
        return priceService.updatePrice(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a price", description = "Delete a price by ID")
    public void deletePrice(@PathVariable @Parameter(description = "Price ID") Integer id) {
        priceService.deletePrice(id);
    }

    @GetMapping("/range")
    @Operation(summary = "Get prices by range", description = "Fetch prices within a range for a selected field and optional sport")
    public List<PriceDTO> getPricesInRange(
            @RequestParam @Parameter(description = "Field to filter: one hour | five hours | ten hours") String field,
            @RequestParam @Parameter(description = "Minimum price") BigDecimal min,
            @RequestParam @Parameter(description = "Maximum price") BigDecimal max,
            @RequestParam(required = false) @Parameter(description = "Optional sport enum value") Sport sport) {
        if (min == null || max == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "min and max are required");
        }
        if (min.compareTo(max) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "min must be less than or equal to max");
        }
        return priceService.findByRangeAndSport(field, min, max, sport);
    }

    @GetMapping("/sort")
    @Operation(summary = "Get prices sorted", description = "Sort prices by a selected field and order, optionally filtered by sport")
    public List<PriceDTO> getPricesSorted(
            @RequestParam @Parameter(description = "Field to sort: one hour | five hours | ten hours") String field,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Order: ascending | descending") String order,
            @RequestParam(required = false) @Parameter(description = "Optional sport enum value") Sport sport) {
        return priceService.findAllSortedByFieldAndSport(field, order, sport);
    }
}
