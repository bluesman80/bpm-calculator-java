package nl.jimkaplan.bpmcalculator.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class KentekenRequest {
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$")
    private String kenteken;
}