package nl.jimkaplan.bpmcalculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FuelDataResponse {
    @JsonProperty("brandstof_omschrijving")
    private String brandstofOmschrijving;
}