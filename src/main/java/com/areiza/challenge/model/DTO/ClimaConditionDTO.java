package com.areiza.challenge.model.DTO;

import com.areiza.challenge.model.enums.Clima;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClimaConditionDTO {

    private LocalDate fecha;
    private Clima clima;

}
