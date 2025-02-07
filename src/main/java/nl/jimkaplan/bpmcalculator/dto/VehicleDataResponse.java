package nl.jimkaplan.bpmcalculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VehicleDataResponse {
    @JsonProperty("catalogusprijs")
    private String catalogusprijs;

    @JsonProperty("bruto_bpm")
    private String brutoBpm;
}
