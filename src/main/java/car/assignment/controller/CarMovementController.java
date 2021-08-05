package car.assignment.controller;

import car.assignment.dto.Coordinate;
import car.assignment.dto.NewPositionRequest;
import car.assignment.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class CarMovementController {

    @Autowired
    private PositionService positionService;

    @PostMapping("/car/new/position")
    public Coordinate newPosition(@Valid @RequestBody NewPositionRequest newPositionRequest) {
        return positionService.moveToNewPosition(newPositionRequest);
    }
}