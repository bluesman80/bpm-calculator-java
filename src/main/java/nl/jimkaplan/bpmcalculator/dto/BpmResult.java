package nl.jimkaplan.bpmcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BpmResult {
    private double nettoCataloguswaarde;
    private double verhoogdBedrag;
    private double brutoBpm;
    private double teBetalen;
}
