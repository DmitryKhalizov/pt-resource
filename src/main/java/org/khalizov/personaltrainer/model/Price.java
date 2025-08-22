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

    @Column(name = "price_online")
    private BigDecimal priceOnline;

    @Column(name = "price_personal")
    private BigDecimal pricePersonal;

    @Column(name = "discounts")
    private String discounts;

}
