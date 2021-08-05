package car.assignment.service;

import car.assignment.domain.Car;
import car.assignment.domain.Direction;
import car.assignment.dto.Coordinate;
import car.assignment.dto.MovementCommand;
import car.assignment.dto.NewPositionRequest;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PositionServiceImpl implements PositionService {

    private static final int MIN_X = 1;
    private static final int MIN_Y = 1;
    private static final int MAX_X = 15;
    private static final int MAX_Y = 15;
    private static final Map<Pair<Direction, MovementCommand>, Direction> NEW_DIRECTION_MOVEMENT_LOOKUP = initDirectionMovementLookup();

    // Build a lookup table for converting a direction and a movement into a new direction
    private static Map<Pair<Direction, MovementCommand>, Direction> initDirectionMovementLookup() {
        Map<Pair<Direction, MovementCommand>, Direction> map = new HashMap<>();
        map.put(new Pair<>(Direction.NORTH, MovementCommand.R), Direction.EAST);
        map.put(new Pair<>(Direction.NORTH, MovementCommand.L), Direction.WEST);
        map.put(new Pair<>(Direction.EAST, MovementCommand.R), Direction.SOUTH);
        map.put(new Pair<>(Direction.EAST, MovementCommand.L), Direction.NORTH);
        map.put(new Pair<>(Direction.SOUTH, MovementCommand.R), Direction.WEST);
        map.put(new Pair<>(Direction.SOUTH, MovementCommand.L), Direction.EAST);
        map.put(new Pair<>(Direction.WEST, MovementCommand.R), Direction.NORTH);
        map.put(new Pair<>(Direction.WEST, MovementCommand.L), Direction.SOUTH);
        return Collections.unmodifiableMap(map);
    }

    @Override
    public Coordinate moveToNewPosition(final NewPositionRequest newPositionRequest) {
        Car car = initialiseCar(newPositionRequest);

        // Iterate movement commands until all are fulfilled
        for (MovementCommand currentCommand : newPositionRequest.getMovementCommands()) {
            car = getCarAtNewPosition(car, currentCommand);
        }

        return car.getCoordinate();
    }

    private Car initialiseCar(final NewPositionRequest newPositionRequest) {
        // Initial car position is set to NORTH at initial position
        return Car.builder().coordinate(Coordinate.builder()
                .x(newPositionRequest.getInitialX())
                .y(newPositionRequest.getInitialY())
                .build())
                .direction(Direction.NORTH)
                .build();
    }

    private Car getCarAtNewPosition(final Car car, final MovementCommand movementCommand) {
        final Direction newDirection = getNewDirection(car.getDirection(), movementCommand);
        final Coordinate newCoordinate = getNewCoordinate(car.getCoordinate(), newDirection, movementCommand);
        return Car.builder()
                .direction(newDirection)
                .coordinate(newCoordinate)
                .build();
    }

    private Direction getNewDirection(final Direction currentDirection, final MovementCommand movementCommand) {
        return NEW_DIRECTION_MOVEMENT_LOOKUP.getOrDefault(new Pair<>(currentDirection, movementCommand), currentDirection);
    }

    private Coordinate getNewCoordinate(final Coordinate coordinate, final Direction direction, final MovementCommand movementCommand) {
        if (movementCommand != MovementCommand.F) {
            // if its a left or right commend there is no movement
            return coordinate;
        }
        int newX = coordinate.getX();
        int newY = coordinate.getY();
        switch (direction) {
            case NORTH:
                newY += 1;
                break;
            case EAST:
                newX += 1;
                break;
            case SOUTH:
                newY -= 1;
                break;
            case WEST:
                newX -= 1;
                break;
        }
        return Coordinate.builder()
                .x(isXInBounds(newX) ? newX : coordinate.getX())
                .y(isYInBounds(newY) ? newY : coordinate.getY())
                .build();
    }

    private boolean isYInBounds(final int y) {
        return y <= MAX_Y && y >= MIN_Y;
    }

    private boolean isXInBounds(final int x) {
        return x <= MAX_X && x >= MIN_X;
    }
}
