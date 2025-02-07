package nl.jimkaplan.bpmcalculator.service;

import lombok.extern.slf4j.Slf4j;
import nl.jimkaplan.bpmcalculator.dto.BpmResult;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BpmCalculatorService {
    private static final double BPM_PERCENTAGE = 0.377;
    private static final double BTW_PERCENTAGE = 0.21;
    public static final String DIESEL = "diesel";

    public BpmResult calculateBpm(double totaleCataloguswaarde, String brandstofType,
                                  int afschrijving, double brutoBpm) {
        validateInput(totaleCataloguswaarde, afschrijving, brutoBpm);

        double nettoCataloguswaarde;
        double bpmBase;

        if (brutoBpm == 0) {
            nettoCataloguswaarde = totaleCataloguswaarde / (1 + BPM_PERCENTAGE + BTW_PERCENTAGE);
            bpmBase = nettoCataloguswaarde * BPM_PERCENTAGE;
        } else {
            nettoCataloguswaarde = (totaleCataloguswaarde - brutoBpm) / (1 + BTW_PERCENTAGE);
            bpmBase = brutoBpm;
        }

        double verhoogdBedrag = DIESEL.equalsIgnoreCase(brandstofType) ? 237 : -1283;
        double bpm = bpmBase + verhoogdBedrag;
        double teBetalen = bpm * (1 - (double) afschrijving / 100);

        return new BpmResult(
                round(nettoCataloguswaarde),
                round(verhoogdBedrag),
                round(bpmBase),
                round(teBetalen)
        );
    }

    private void validateInput(double totaleCataloguswaarde, int afschrijving, double brutoBpm) {
        if (totaleCataloguswaarde < 0 || afschrijving < 0 || afschrijving > 100 || brutoBpm < 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
    }

    private double round(double value) {
        return Math.round(value * 100) / 100.0;
    }
}