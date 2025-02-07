package nl.jimkaplan.bpmcalculator.controller;

import lombok.extern.slf4j.Slf4j;
import nl.jimkaplan.bpmcalculator.dto.BpmResult;
import nl.jimkaplan.bpmcalculator.dto.CalculationRequest;
import nl.jimkaplan.bpmcalculator.dto.ErrorResponse;
import nl.jimkaplan.bpmcalculator.dto.KentekenRequest;
import nl.jimkaplan.bpmcalculator.dto.RdwVehicleData;
import nl.jimkaplan.bpmcalculator.exception.RdwApiException;
import nl.jimkaplan.bpmcalculator.service.BpmCalculatorService;
import nl.jimkaplan.bpmcalculator.service.RdwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class BpmController {
    private final RdwService rdwService;
    private final BpmCalculatorService bpmService;

    @Autowired
    public BpmController(RdwService rdwService, BpmCalculatorService bpmService) {
        this.rdwService = rdwService;
        this.bpmService = bpmService;
    }

    /**
     * Lookup vehicle data based on the kenteken
     *
     * @param request KentekenRequest object
     * @param bindingResult BindingResult object
     *                <p>
     *                The `BindingResult` argument in the `calculateBpm` method is used to hold the results of
     *                validation for the `CalculationRequest` object. When the `@Validated` annotation is used on
     *                the `@RequestBody` parameter, Spring Boot automatically validates the request body against
     *                the constraints defined in the `CalculationRequest` class.
     *                <p>
     *                If there are any validation errors, they are captured in the `BindingResult` object.
     *                This allows the method to check for validation errors and handle them appropriately, such as
     *                returning a `400 Bad Request` response with an error message.
     * @return ResponseEntity with the vehicle data or an error message
     */
    @PostMapping("/lookupKenteken")
    public ResponseEntity<?> lookupKenteken(@Validated @RequestBody KentekenRequest request, BindingResult bindingResult) {
        if (handleValidationResult(bindingResult).isPresent())
            return handleValidationResult(bindingResult).get();

        try {
            String cleanedKenteken = request.getKenteken().replaceAll("[-\\s]", "").toUpperCase();
            RdwVehicleData data = rdwService.getVehicleData(cleanedKenteken);
            return ResponseEntity.ok(data);
        } catch (RdwApiException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * Calculate BPM based on the input values
     *
     * @param request CalculationRequest object
     * @param bindingResult  BindingResult object
     *                <p>
     *                The `BindingResult` argument in the `calculateBpm` method is used to hold the results of
     *                validation for the `CalculationRequest` object. When the `@Validated` annotation is used on
     *                the `@RequestBody` parameter, Spring Boot automatically validates the request body against
     *                the constraints defined in the `CalculationRequest` class.
     *                <p>
     *                If there are any validation errors, they are captured in the `BindingResult` object.
     *                This allows the method to check for validation errors and handle them appropriately, such as
     *                returning a `400 Bad Request` response with an error message.
     * @return ResponseEntity with the calculated BPM or an error message
     */
    @PostMapping("/calculate")
    public ResponseEntity<?> calculateBpm(@Validated @RequestBody CalculationRequest request, BindingResult bindingResult) {
        if (handleValidationResult(bindingResult).isPresent())
            return handleValidationResult(bindingResult).get();

        try {
            BpmResult bpmResult = bpmService.calculateBpm(
                    request.getCataloguswaarde(),
                    request.getBrandstof(),
                    request.getAfschrijving(),
                    request.getBrutoBpm() != null ? request.getBrutoBpm() : 0.0
            );
            return ResponseEntity.ok(bpmResult);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }

    private Optional<ResponseEntity<ErrorResponse>> handleValidationResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Optional.of(ResponseEntity.badRequest().body(new ErrorResponse("Invalid input")));
        }
        return Optional.empty();
    }
}
