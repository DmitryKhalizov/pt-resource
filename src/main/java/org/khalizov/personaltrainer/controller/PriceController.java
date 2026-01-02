package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.PriceCreateDTO;
import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/sport/{sport}")
    @Operation(summary = "Get all prices by sport", description = "Fetch all prices for trainers of a specific sport")
    public List<PriceDTO> getPricesBySport(@PathVariable @Parameter(description = "Sport name (e.g., CROSSFIT, RUNNING, FUNCTIONAL_FITNESS)") String sport) {
        return priceService.getPricesBySport(sport);
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

    @GetMapping("/range/hour")
    @Operation(summary = "Get prices by per-hour range", description = "Fetch prices within a specific per-hour price range")
    public List<PriceDTO> getPricesPerHourBetween(
            @RequestParam @Parameter(description = "Minimum price per hour") BigDecimal min,
            @RequestParam @Parameter(description = "Maximum price per hour") BigDecimal max) {
        return priceService.findByPricePerHourBetween(min, max);
    }

    @GetMapping("/range/five")
    @Operation(summary = "Get prices by five-hour range", description = "Fetch prices within a specific five-hour price range")
    public List<PriceDTO> getPricesFiveHoursBetween(
            @RequestParam @Parameter(description = "Minimum price for five hours") BigDecimal min,
            @RequestParam @Parameter(description = "Maximum price for five hours") BigDecimal max) {
        return priceService.findByPriceFiveHoursBetween(min, max);
    }

    @GetMapping("/range/ten")
    @Operation(summary = "Get prices by ten-hour range", description = "Fetch prices within a specific ten-hour price range")
    public List<PriceDTO> getPricesTenHoursBetween(
            @RequestParam @Parameter(description = "Minimum price for ten hours") BigDecimal min,
            @RequestParam @Parameter(description = "Maximum price for ten hours") BigDecimal max) {
        return priceService.findByPriceTenHoursBetween(min, max);
    }

    @GetMapping("/sort/hour/asc")
    @Operation(summary = "Get all prices sorted by hour (ascending)", description = "Fetch all prices sorted by per-hour price in ascending order")
    public List<PriceDTO> getPricesSortedByHourAsc() {
        return priceService.findAllOrderByPricePerHourAsc();
    }

    @GetMapping("/sort/hour/desc")
    @Operation(summary = "Get all prices sorted by hour (descending)", description = "Fetch all prices sorted by per-hour price in descending order")
    public List<PriceDTO> getPricesSortedByHourDesc() {
        return priceService.findAllOrderByPricePerHourDesc();
    }

    @GetMapping("/sort/five/asc")
    @Operation(summary = "Get all prices sorted by five hours (ascending)", description = "Fetch all prices sorted by five-hour price in ascending order")
    public List<PriceDTO> getPricesSortedByFiveHoursAsc() {
        return priceService.findAllOrderByPriceFiveHoursAsc();
    }

    @GetMapping("/sort/five/desc")
    @Operation(summary = "Get all prices sorted by five hours (descending)", description = "Fetch all prices sorted by five-hour price in descending order")
    public List<PriceDTO> getPricesSortedByFiveHoursDesc() {
        return priceService.findAllOrderByPriceFiveHoursDesc();
    }

    @GetMapping("/sort/ten/asc")
    @Operation(summary = "Get all prices sorted by ten hours (ascending)", description = "Fetch all prices sorted by ten-hour price in ascending order")
    public List<PriceDTO> getPricesSortedByTenHoursAsc() {
        return priceService.findAllOrderByPriceTenHoursAsc();
    }

    @GetMapping("/sort/ten/desc")
    @Operation(summary = "Get all prices sorted by ten hours (descending)", description = "Fetch all prices sorted by ten-hour price in descending order")
    public List<PriceDTO> getPricesSortedByTenHoursDesc() {
        return priceService.findAllOrderByPriceTenHoursDesc();
    }
}
