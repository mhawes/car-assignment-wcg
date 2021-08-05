package car.assignment.service;

import car.assignment.dto.Coordinate;
import car.assignment.dto.NewPositionRequest;


public interface PositionService {
    Coordinate moveToNewPosition(NewPositionRequest newPositionRequest);
}
