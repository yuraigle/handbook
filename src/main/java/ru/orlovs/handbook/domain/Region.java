package ru.orlovs.handbook.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Capital is required")
    @Column(name = "capital", nullable = false)
    private String capital;

    @Column(name = "area")
    private BigDecimal area;
}
