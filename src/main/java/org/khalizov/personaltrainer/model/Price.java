package org.khalizov.personaltrainer.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "price")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Integer priceId;

    @Column(name = "price_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @Column(name = "price_five_hours", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceFiveHours;

    @Column(name = "price_ten_hours", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceTenHours;

    @Column(name = "special_price")
    private String specialPrice;

    @OneToOne(mappedBy = "price", fetch = FetchType.LAZY)
    private PersonalTrainer trainer;

}
