package nl.jimkaplan.bpmcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RdwVehicleData {
    private Double cataloguswaarde;
    private Double brutoBpm;
    private String brandstofOmschrijving;
}