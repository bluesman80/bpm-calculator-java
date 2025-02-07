package nl.jimkaplan.bpmcalculator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jimkaplan.bpmcalculator.dto.FuelDataResponse;
import nl.jimkaplan.bpmcalculator.dto.RdwVehicleData;
import nl.jimkaplan.bpmcalculator.dto.VehicleDataResponse;
import nl.jimkaplan.bpmcalculator.exception.RdwApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class RdwService {
    public final String RDW_VEHICLE_URL = "https://opendata.rdw.nl/resource/m9d7-ebf2.json?kenteken=";
    public final String RDW_FUEL_TYPE_URL = "https://opendata.rdw.nl/resource/8ys7-d773.json?kenteken=";
    @Value("${rdw.api.token}")
    private String apiToken;
    private final RestTemplate restTemplate;

    @Autowired
    public RdwService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RdwVehicleData getVehicleData(String kenteken) throws RdwApiException {
        String vehicleUrl = RDW_VEHICLE_URL + kenteken;
        String fuelUrl = RDW_FUEL_TYPE_URL + kenteken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Fetch vehicle data
        ResponseEntity<List<VehicleDataResponse>> vehicleResponse = restTemplate.exchange(
                vehicleUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                }
        );

        // Check if the response is valid and not null
        handleResponse(vehicleResponse);
        VehicleDataResponse vehicleData = vehicleResponse.getBody().getFirst();

        // Fetch fuel data
        ResponseEntity<List<FuelDataResponse>> fuelResponse = restTemplate.exchange(
                fuelUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                }
        );

        // Check if the response is valid and not null
        handleResponse(fuelResponse);
        FuelDataResponse fuelData = fuelResponse.getBody().getFirst();

        return new RdwVehicleData(
                parseDouble(vehicleData.getCatalogusprijs()),
                parseDouble(vehicleData.getBrutoBpm()),
                fuelData.getBrandstofOmschrijving()
        );
    }

    private void handleResponse(ResponseEntity<?> response) throws RdwApiException {
        if (response == null) {
            throw new RdwApiException("RDW API error. Fuel type cannot be found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response.getBody() == null || response.getStatusCode() != HttpStatus.OK) {
            throw new RdwApiException("RDW API error. Fuel type cannot be found", (HttpStatus) response.getStatusCode());
        }
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            log.error("Error parsing value of catalogusPrijs or brutoBpm: {}", value);
            return 0.0;
        }
    }
}
