package com.areiza.challenge.model.entity;


import com.areiza.challenge.model.enums.Clima;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Table(name = "clima_condition")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClimaCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate fecha;

    @Column(nullable = false)
    private Clima clima;

}

