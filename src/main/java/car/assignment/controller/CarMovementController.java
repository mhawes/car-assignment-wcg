package car.assignment.controller;

import car.assignment.dto.Coordinate;
import car.assignment.dto.NewPositionRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class CarMovementController {

    @PostMapping("/car/new/position")
    public Coordinate newPosition(@Valid @RequestBody NewPositionRequest newPositionRequest) {
        return null;
    }
}