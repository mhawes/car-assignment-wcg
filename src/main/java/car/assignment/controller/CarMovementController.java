package car.assignment.controller;

import car.assignment.dto.NewPositionRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class CarMovementController {

    @PostMapping("/car/new/position")
    public Point newPosition(@RequestBody NewPositionRequest newPositionRequest) {
        return null;
    }
}