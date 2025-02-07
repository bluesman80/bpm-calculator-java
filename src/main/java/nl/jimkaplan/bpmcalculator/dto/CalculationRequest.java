package nl.jimkaplan.bpmcalculator.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

@Data
public class CalculationRequest {
    @NonNull
    private Double cataloguswaarde;

    @NonNull
    @Pattern(regexp = "(?i)^(diesel|benzine)$")
    private String brandstof;

    @NonNull
    @Min(0)
    @Max(100)
    private Integer afschrijving;

    private Double brutoBpm;
}