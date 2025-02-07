package nl.jimkaplan.bpmcalculator;

import nl.jimkaplan.bpmcalculator.dto.FuelDataResponse;
import nl.jimkaplan.bpmcalculator.dto.RdwVehicleData;
import nl.jimkaplan.bpmcalculator.dto.VehicleDataResponse;
import nl.jimkaplan.bpmcalculator.exception.RdwApiException;
import nl.jimkaplan.bpmcalculator.service.RdwService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RdwServiceTest {

    private RestTemplate restTemplate;
    private RdwService rdwService;
    public static final Double CATALOGUSPRIJS = 36273.0;
    public static final Double BRUTO_BPM = 8616.84;
    public static final String KENTEKEN = "AB123C";

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new CustomRestTemplate();
        rdwService = new RdwService(restTemplate);
    }

    @Test
    void testGetVehicleDataWithValidKenteken() throws RdwApiException {
        RdwVehicleData result = rdwService.getVehicleData(KENTEKEN);

        assertNotNull(result);
        assertEquals(CATALOGUSPRIJS, result.getCataloguswaarde());
        assertEquals(BRUTO_BPM, result.getBrutoBpm());
        assertEquals("Diesel", result.getBrandstofOmschrijving());
    }

    @Test
    void testGetVehicleDataWithInvalidKenteken() {
        String kenteken = "INVALID";

        RdwApiException exception = assertThrows(RdwApiException.class, () -> {
            rdwService.getVehicleData(kenteken);
        });

        assertEquals("RDW API error. Fuel type cannot be found", exception.getMessage());
    }

    @Configuration
    class TestConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new CustomRestTemplate();
        }
    }

    class CustomRestTemplate extends RestTemplate {
        @Override
        public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
            if (url.equals(rdwService.RDW_VEHICLE_URL + KENTEKEN)) {
                VehicleDataResponse vehicleDataResponse = new VehicleDataResponse();
                vehicleDataResponse.setCatalogusprijs(String.valueOf(CATALOGUSPRIJS));
                vehicleDataResponse.setBrutoBpm(String.valueOf(BRUTO_BPM));
                List<VehicleDataResponse> vehicleDataResponses = List.of(vehicleDataResponse);

                return new ResponseEntity(vehicleDataResponses, HttpStatus.OK);
            } else if (url.equals(rdwService.RDW_FUEL_TYPE_URL + KENTEKEN)) {
                FuelDataResponse fuelDataResponse = new FuelDataResponse();
                fuelDataResponse.setBrandstofOmschrijving("Diesel");
                List<FuelDataResponse> fuelDataResponses = List.of(fuelDataResponse);

                return new ResponseEntity(fuelDataResponses, HttpStatus.OK);
            } else if (KENTEKEN.equals("INVALID")) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}