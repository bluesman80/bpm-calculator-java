package nl.jimkaplan.bpmcalculator;

import nl.jimkaplan.bpmcalculator.dto.BpmResult;
import nl.jimkaplan.bpmcalculator.service.BpmCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BpmCalculatorServiceTest {

    @Autowired
    private BpmCalculatorService bpmCalculatorService;

    @Test
    void testCalculateBpmWithValidInputDiesel() {
        /*
            In totaal is de afschrijving: 81% + (29*0,19x1%)= 86,51% =87%
            Cataloguswaarde: bruto 36273 netto 22856
            Bpm = 22856*0,377=8616,84
            Verhogen met 237 (diesel) = 8616,84+237=8.853,84
            De te betalen bpm= het bruto bpm bedrag – (afschrijving%)
            = 8853,84-(8853,84*0,87)=1.150,999
        */
        double totaleCataloguswaarde = 36273.0;
        String brandstofType = "diesel";
        int afschrijving = 87;
        double brutoBpm = 0.0;

        BpmResult result = bpmCalculatorService.calculateBpm(totaleCataloguswaarde, brandstofType, afschrijving, brutoBpm);

        assertNotNull(result);
        assertEquals(22856.33, result.getNettoCataloguswaarde());
        assertEquals(237.0, result.getVerhoogdBedrag());
        assertEquals(8616.84, result.getBrutoBpm());
        assertEquals(1151.0, result.getTeBetalen());
    }

    @Test
    void testCalculateBpmWithValidInputBenzine() {
        double totaleCataloguswaarde = 30000.0;
        String brandstofType = "benzine";
        int afschrijving = 20;
        double brutoBpm = 0.0;

        BpmResult result = bpmCalculatorService.calculateBpm(totaleCataloguswaarde, brandstofType, afschrijving, brutoBpm);

        assertNotNull(result);
        assertEquals(18903.59, result.getNettoCataloguswaarde());
        assertEquals(-1283.0, result.getVerhoogdBedrag());
        assertEquals(7126.65, result.getBrutoBpm());
        assertEquals(4674.92, result.getTeBetalen());
    }

    @Test
    void testCalculateBpmWithBrutoBpm() {
        double totaleCataloguswaarde = 50220.0;
        String brandstofType = "benzine";
        int afschrijving = 20;
        double brutoBpm = 1963.0;

        BpmResult result = bpmCalculatorService.calculateBpm(totaleCataloguswaarde, brandstofType, afschrijving, brutoBpm);

        assertNotNull(result);
        assertEquals(39881.82, result.getNettoCataloguswaarde());
        assertEquals(-1283.0, result.getVerhoogdBedrag());
        assertEquals(1963.0, result.getBrutoBpm());
        assertEquals(544.0, result.getTeBetalen());
    }

    @Test
    void testCalculateBpmWithInvalidInput() {
        double totaleCataloguswaarde = -30000.0;
        String brandstofType = "benzine";
        int afschrijving = 20;
        double brutoBpm = 0.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bpmCalculatorService.calculateBpm(totaleCataloguswaarde, brandstofType, afschrijving, brutoBpm);
        });

        assertEquals("Invalid input values", exception.getMessage());
    }
}