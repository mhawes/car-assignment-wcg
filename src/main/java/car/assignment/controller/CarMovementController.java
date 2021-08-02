package car.assignment.controller;

import car.assignment.dto.NewPositionRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class CarMovementController {

    @GetMapping("/car/new/position")
    public Point getNewPosition(@RequestBody NewPositionRequest newPositionRequest) {
        return null;
    }

}